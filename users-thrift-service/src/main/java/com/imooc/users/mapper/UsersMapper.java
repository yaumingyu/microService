package com.imooc.users.mapper;

import com.imooc.thrift.users.UsersInfo;
import com.imooc.thrift.users.dto.TeacherDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by Administrator on 2018/10/1 0001.
 */
@Mapper
public interface UsersMapper {

    @Select("select id, username, real_name as realName, password, email, mobile from pe_users where id = #{id}")
    UsersInfo getUserById(@Param("id") int id);

    @Select("select id, username, real_name as realName, password, email, mobile from pe_users where username = #{username}")
    UsersInfo getUsersByUsername(@Param("username") String username);


    @Insert("insert into pe_users (username, password, real_name, mobile, email) " +
            "values (#{usersInfo.username}, #{usersInfo.password}, #{usersInfo.realName}, #{usersInfo.mobile}, #{usersInfo.email})")
    void userRegister(@Param("usersInfo") UsersInfo usersInfo);

    @Select("select u.id, u.username, u.real_name as realName, u.password, u.email, u.mobile," +
            "t.intro, t.stars from pe_users u, pe_teacher t where u.id = t.user_id and u.id = #{id}")
    UsersInfo getTeacherById(@Param("id") int id);
}
