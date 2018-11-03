package com.imooc.course.filter;

import com.imooc.thrift.users.dto.UsersDTO;
import com.imooc.users.filter.LoginFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2018/10/7 0007.
 */
public class CourseFilter extends LoginFilter {
    @Override
    protected void login(HttpServletRequest request, HttpServletResponse response, UsersDTO usersDTO) {
        request.setAttribute("users",usersDTO);
    }
}
