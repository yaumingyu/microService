package com.imooc.users.filter;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.imooc.thrift.users.dto.UsersDTO;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2018/10/4 0004.
 */
public abstract class LoginFilter implements Filter {

    //客户端缓存
    private static Cache<String,UsersDTO> cache = CacheBuilder.newBuilder().maximumSize(10000).expireAfterWrite(3, TimeUnit.MINUTES).build();
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //将request和response转换为http
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        //从paramter获取token
        String token = httpServletRequest.getParameter("token");
        //支持cookis
        if(StringUtils.isBlank(token)){
            Cookie[] cookies = httpServletRequest.getCookies();
            if(cookies != null) {
                for(Cookie c : cookies){
                    if(c.getName().equals("token")){
                        token = c.getValue();
                    }
                }
            }
        }
        //token换取用户信息
        UsersDTO usersDTO = null;
        if(StringUtils.isNotBlank(token)){
            usersDTO = cache.getIfPresent(token);
            if(usersDTO == null){
                usersDTO = requestUserInfo(token);
                if(usersDTO != null){
                    cache.put(token,usersDTO);
                }
            }
        }
        if(usersDTO == null){
            httpServletResponse.sendRedirect("http://www.test.com/users/login");
            return;
        }

        login(httpServletRequest,httpServletResponse,usersDTO);
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }

    protected abstract void login(HttpServletRequest request, HttpServletResponse response, UsersDTO usersDTO);

    private UsersDTO requestUserInfo(String token){
        String url = "http://users-edge-service:8082/users/authenticate";
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("token",token);
        InputStream inputStream = null;
        try {
            HttpResponse httpResponse = httpClient.execute(httpPost);
            if(httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK){
                throw new RuntimeException("Request userInfo is failure.response status is "+ httpResponse.getStatusLine().getStatusCode());
            }
            inputStream = httpResponse.getEntity().getContent();
            byte[] temp = new byte[1024];
            int len = 0;
            StringBuilder sb = new StringBuilder();
            while((len = inputStream.read(temp)) > 0){
                sb.append(new String(temp,0,len));
            }
            UsersDTO usersDTO = new ObjectMapper().readValue(sb.toString(),UsersDTO.class);
            return usersDTO;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public void destroy() {

    }
}
