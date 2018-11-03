package com.imooc.users.service;

import com.imooc.users.mapper.UsersMapper;
import com.imooc.thrift.users.UsersInfo;
import com.imooc.thrift.users.UsersService;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2018/10/1 0001.
 */
@Service
public class UsersServiceImpl implements UsersService.Iface{

    @Autowired
    private UsersMapper usersMapper;

    @Override
    public UsersInfo getUsersById(int id) throws TException {
        return usersMapper.getUserById(id);
    }

    @Override
    public UsersInfo getUsersByUsername(String username) throws TException {
        UsersInfo usersInfo = usersMapper.getUsersByUsername(username);
        System.out.print(usersInfo);
        return usersInfo;
    }

    @Override
    public UsersInfo getTeacherById(int id) throws TException {
        return usersMapper.getTeacherById(id);
    }

    @Override
    public void usersRegister(UsersInfo usersInfo) throws TException {
        usersMapper.userRegister(usersInfo);
    }
}
