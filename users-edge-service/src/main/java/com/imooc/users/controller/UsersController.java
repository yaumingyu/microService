package com.imooc.users.controller;

import com.imooc.thrift.users.UsersInfo;

import com.imooc.thrift.users.dto.UsersDTO;
import com.imooc.users.redis.RedisClient;
import com.imooc.users.response.LoginResponse;
import com.imooc.users.response.Response;
import com.imooc.users.thrift.UsersServiceProvider;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.apache.tomcat.util.buf.HexUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;


/**
 * Created by Administrator on 2018/10/1 0001.
 */
@Controller
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersServiceProvider serviceProvider;

    @Autowired
    private RedisClient redisClient;

    @RequestMapping(value="/sentCode", method=RequestMethod.POST)
    @ResponseBody
    public Response sentCode(@RequestParam(value = "mobile",required = false)String mobile,
                             @RequestParam(value = "email",required = false)String email){
        if(StringUtils.isBlank(mobile) && StringUtils.isBlank(email)){
            return Response.MOBILE_EMAIL_IS_EMPTY;
        }
        boolean isSentSuccess = false;
        try {
            if(StringUtils.isNotBlank(mobile)){
                String message = "Verify code is";
                String code = "123456789";
                isSentSuccess = serviceProvider.getMessageService().sentMobileMessage(mobile,message+code);
                redisClient.set(mobile,code);
            }
            if(StringUtils.isNotBlank(email)){
                String message = "Verify code is";
                String code = "987654321";
                isSentSuccess = serviceProvider.getMessageService().sentEmailMessage(email,message+code);
                redisClient.set(email,code);
            }
        } catch (TException e) {
            e.printStackTrace();
            return Response.exception(e);
        }
        Response response = null;
        if(isSentSuccess == true){
            response = new Response();
        }else{
            response = Response.SENT_CODE_IS_ERROR;
        }
        return response;
    }

    /**
     * 注册
     * @param username
     * @param password
     * @param realName
     * @param mobile
     * @param email
     * @return
     */
    @RequestMapping(value="/register", method=RequestMethod.POST)
    @ResponseBody
    public Response register(@RequestParam("username")String username,
                             @RequestParam("password")String password,
                             @RequestParam("realName")String realName,
                             @RequestParam(value = "mobile",required = false)String mobile,
                             @RequestParam(value = "email",required = false)String email,
                             @RequestParam("verifyCode")String verifyCode){
        //校验校验码是否正确
        if(StringUtils.isBlank(mobile) && StringUtils.isBlank(email)){
            return Response.MOBILE_EMAIL_IS_EMPTY;
        }
        if(StringUtils.isBlank(verifyCode)){
            return Response.VERIFY_CODE_IS_EMPTY;
        }
        if(StringUtils.isNotBlank(mobile)){
            if(!redisClient.get(mobile).equals(verifyCode)){
                return Response.VERIFY_CODE_IS_ERROR;
            }
        } else if(StringUtils.isNotBlank(email)){
            if(!redisClient.get(email).equals(verifyCode)){
                return Response.VERIFY_CODE_IS_ERROR;
            }
        }
        UsersInfo usersInfo = new UsersInfo();
        usersInfo.setEmail(email);
        usersInfo.setUsername(username);
        usersInfo.setRealName(realName);
        usersInfo.setMobile(mobile);
        usersInfo.setPassword(md5(password));
        try {
            serviceProvider.getUsersService().usersRegister(usersInfo);
        } catch (TException e) {
            e.printStackTrace();
            return Response.exception(e);
        }
        return new Response();

    }

    @RequestMapping(value="login", method = RequestMethod.GET)
    public String login (){
        return "login";
    }

    /**
     *  用户登录
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public Response login(@RequestParam("username") String username, @RequestParam("password") String password){
        //1.调用用户服务，教研用户名密码是否正确
        UsersInfo usersInfo = null;
        try{
            usersInfo = serviceProvider.getUsersService().getUsersByUsername(username);
        }catch (TException e){
            e.printStackTrace();
        }
        if(usersInfo == null){
            return Response.USERNAME_IS_NOT_EXIT;
        }
        if(!usersInfo.getPassword().equals(md5(password))){
            return Response.PASSWORD_IS_ERROR;
        }
        //生成Token
        String token = genToken();

        //缓存用户
        redisClient.set(token,toDTO(usersInfo));
        return new LoginResponse(token);
    }

    @RequestMapping(value = "authenticate",method = RequestMethod.POST)
    @ResponseBody
    public UsersDTO authenticate(@RequestHeader("token") String token){
        UsersDTO usersDTO = redisClient.get(token);
        return usersDTO;
    }

    private UsersDTO toDTO(UsersInfo usersInfo){
        UsersDTO usersDTO = new UsersDTO();
        BeanUtils.copyProperties(usersInfo,usersDTO);
        return usersDTO;
    }
   /* private String genToken(){
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String jws = Jwts.builder().setSubject("test").signWith(key).compact();
        return jws;
    }*/

    private String genToken() {
        return randomCode("0123456789abcdefghijklmnopqrstuvwxyz", 32);
    }

    private String randomCode(String s, int size) {
        StringBuilder result = new StringBuilder(size);

        Random random = new Random();
        for(int i=0;i<size;i++) {
            int loc = random.nextInt(s.length());
            result.append(s.charAt(loc));
        }
        return result.toString();
    }


    private String md5(String password){
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] passwordByte = md5.digest(password.getBytes("utf-8"));
            return HexUtils.toHexString(passwordByte);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
