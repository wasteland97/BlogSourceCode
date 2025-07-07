package com.wasteland.blogsourcecode.mybatisdemo.mapper;

import com.wasteland.blogsourcecode.mybatisdemo.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author wasteland
 * @create 2025-04-08
 */
@Mapper
public interface UserMapper {

//     @Select("select * from user where id = #{id}")
    User findById(@Param("id") Integer id);
    //  @Select("select * from user")
    List<User> findAll();

    // @Delete("delete from user where id = #{id}")
    void DelteById(Integer id);

    // @Insert("insert into user(name,age,gender,phone) values(#{name},#{age},#{gender},#{phone})")
    void AddUser(User user);

    // @Update("update user set name = #{name},age = #{age},gender = #{gender},phone = #{phone} where id = #{id}")
    void UpdateUser(User user);
}

