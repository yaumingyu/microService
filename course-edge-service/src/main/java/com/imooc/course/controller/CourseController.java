package com.imooc.course.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.imooc.course.service.ICourseService;
import com.imooc.course.dto.CourseDTO;
import com.imooc.thrift.users.dto.UsersDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Administrator on 2018/10/7 0007.
 */
@Controller
@RequestMapping("/course")
public class CourseController {


    @Reference
    private ICourseService courseService;

    @RequestMapping(value = "/getCourseList",method = RequestMethod.GET)
    @ResponseBody
    public List<CourseDTO> getCourseList(HttpServletRequest request){
        UsersDTO usersDTO = (UsersDTO) request.getAttribute("users");
        System.out.print(usersDTO);
        List<CourseDTO> courseList = courseService.getCourseList();
        return courseList;
    }
}
