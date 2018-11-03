package com.imooc.users.response;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/10/2 0002.
 */
public class Response implements Serializable{

    private int code;
    private String message;
    private Object content;
    public static final Response USERNAME_IS_NOT_EXIT = new Response(1001,"Username is not exit");
    public static final Response PASSWORD_IS_ERROR = new Response(1002,"Password is error");
    public static final Response LOGIN_SUCCESS = new Response();
    public static final Response MOBILE_EMAIL_IS_EMPTY = new Response(1003,"Phone and email are empty ");
    public static final Response SENT_CODE_IS_ERROR  = new Response(1004,"Sent code is failure");
    public static final Response VERIFY_CODE_IS_EMPTY  = new Response(1005,"Verify code is empty");
    public static final Response VERIFY_CODE_IS_ERROR  = new Response(1006,"Verify code is error");

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public Response(){
        this.code = 200;
        this.message = "SUCCESS";
    }

    public Response(int code, String message){
        this.code = code;
        this.message = message;
    }

    public Response(int code, String message,Object content){
        this.code = code;
        this.message = message;
        this.content = content;
    }
    public static Response exception(Exception e){
        String exceptionMessage = e.getMessage();
        return new Response(9999,exceptionMessage);
    }
}
