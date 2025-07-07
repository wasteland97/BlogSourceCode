package com.wasteland.blogsourcecode.mybatisdemo.controller;

import com.wasteland.blogsourcecode.mybatisdemo.pojo.User;
import com.wasteland.blogsourcecode.mybatisdemo.service.UserService;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wfhstart
 * @create 2025-04-08
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;
    private static final Map<Integer, User> userMap;
    static {
        userMap = new HashMap<>();
        User user1 = new User(1,"张三", (short)14, (short)1, "13344554455");
        User user2 = new User(2,"李四", (short)17, (short)2, "15544555544");
        userMap.put(user1.getId(), user1);
        userMap.put(user2.getId(), user2);
    }
    @RequestMapping("/findById")
    public User findById(Integer id){
        if(id < 0) {
            return new User();
        }

        return userMap.get(id);
    }
    @RequestMapping("/findAll")
    public List<User> findAll() {
        return new ArrayList<>(userMap.values());
    }
    @RequestMapping("/AddUser")
    public String AddUser(User user){
        return  userService.AddUser(user);
    }
    @RequestMapping("/DelteById")
    public String DelteById(Integer id){
        return  userService.DelteById(id);
    }
    @RequestMapping("/UpdateUser")
    public String UpdateUser(User user){
        return  userService.UpdateUser(user);
    }

}
