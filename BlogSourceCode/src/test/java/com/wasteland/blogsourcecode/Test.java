package com.wasteland.blogsourcecode;

import com.wasteland.blogsourcecode.mybatisdemo.mapper.UserMapper;
import com.wasteland.blogsourcecode.mybatisdemo.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author wfhstart
 * @create 2025-04-08
 */
public class Test {

//    @org.junit.jupiter.api.Test
//    public void test() throws IOException {
//
////        BeanPostProcessor
//        String resource = "mybatis-config.xml";
//        InputStream inputStream = Resources.getResourceAsStream(resource);
//        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
//
//        SqlSession sqlSession = sqlSessionFactory.openSession();
//
//        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
//
//
////        List<User> users = userMapper.findAll();
//        User users = userMapper.findById(2);
//        System.out.println(users);
//
//        sqlSession.close();
//    }
}
