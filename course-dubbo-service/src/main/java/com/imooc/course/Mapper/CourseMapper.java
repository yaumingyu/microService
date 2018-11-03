package com.imooc.course.Mapper;

import com.imooc.course.dto.CourseDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


import java.util.List;

/**
 * Created by Administrator on 2018/10/7 0007.
 */
@Mapper
public interface CourseMapper {

    @Select("select * from pe_course")
    List<CourseDTO> getCourseList();

    @Select("select teacher_id from pe_teacher_course where course_id = #{courseId}")
    Integer getTeacherIdByCourseId(@Param("courseId") int courseId);
}
