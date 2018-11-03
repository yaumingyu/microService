package com.imooc.thrift.users.dto;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/10/7 0007.
 */
public class TeacherDTO extends UsersDTO implements Serializable{
    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    private String intro;
    private int stars;
}
