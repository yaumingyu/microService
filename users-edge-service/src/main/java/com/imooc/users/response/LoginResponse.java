package com.imooc.users.response;

/**
 * Created by Administrator on 2018/10/3 0003.
 */
public class LoginResponse extends Response{
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String token;

    public LoginResponse(String token){
        this.token = token;
    }
}
