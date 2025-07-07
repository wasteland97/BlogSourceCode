package com.wasteland.blogsourcecode.mybatisdemo.service;

import com.wasteland.blogsourcecode.mybatisdemo.pojo.User;

import java.util.List;

/**
 * @author wfhstart
 * @create 2025-04-08
 */
public interface UserService {
    User findById(Integer id);

    List<User> findAll();

    String DelteById(Integer id);
    String AddUser(User user);
    String UpdateUser(User user);
}
