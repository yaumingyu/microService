package com.imooc.course.service;

import com.imooc.course.dto.CourseDTO;
import com.imooc.course.Mapper.CourseMapper;
import com.imooc.course.thrift.UsersServiceProvider;
import com.imooc.thrift.users.UsersInfo;
import com.imooc.thrift.users.dto.TeacherDTO;
import org.apache.thrift.TException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/10/7 0007.
 */
@Service
public class CourseServiceImpl implements ICourseService{
    
    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private UsersServiceProvider usersServiceProvider;
    
    @Override
    public List<CourseDTO> getCourseList() {
        List<CourseDTO> courseList = courseMapper.getCourseList();
        for(CourseDTO courseDTO : courseList){
            Integer teacherId = courseMapper.getTeacherIdByCourseId(courseDTO.getId());
            if(teacherId != null){
                try {
                    UsersInfo usersInfo = usersServiceProvider.getUsersService().getTeacherById(teacherId);
                    TeacherDTO teacherDTO = transToTeacherDTO(usersInfo);
                    courseDTO.setTeacherDTO(teacherDTO);
                } catch (TException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        return courseList;
    }

    private  TeacherDTO transToTeacherDTO(UsersInfo usersInfo){
        TeacherDTO teacherDTO = new TeacherDTO();
        BeanUtils.copyProperties(usersInfo, teacherDTO);
        return teacherDTO;
    }
}
