package com.imooc.course.dto;

import com.imooc.thrift.users.dto.TeacherDTO;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/10/7 0007.
 */
public class CourseDTO implements Serializable{
    private int id;
    private String title;
    private String intro;
    private TeacherDTO teacherDTO;

    public TeacherDTO getTeacherDTO() {
        return teacherDTO;
    }

    public void setTeacherDTO(TeacherDTO teacherDTO) {
        this.teacherDTO = teacherDTO;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }
}
