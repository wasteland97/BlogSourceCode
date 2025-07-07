package com.wasteland.blogsourcecode.mybatisdemo.service.impl;

import com.wasteland.blogsourcecode.mybatisdemo.mapper.UserMapper;
import com.wasteland.blogsourcecode.mybatisdemo.pojo.User;
import com.wasteland.blogsourcecode.mybatisdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wfhstart
 * @create 2025-04-08
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findById(Integer id) {
        return userMapper.findById(id);
    }

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }

    @Override
    public String DelteById(Integer id) {
        userMapper.DelteById(id);
        return "删除成功";
    }

    @Override
    public String AddUser(User user) {
        userMapper.AddUser(user);
        return "添加成功";
    }

    @Override
    public String UpdateUser(User user) {
        userMapper.UpdateUser(user);
        return "更新成功";
    }
}
