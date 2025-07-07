package com.wasteland.blogsourcecode.mybatisdemo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wfhstart
 * @create 2025-04-08
 */
@Data
@NoArgsConstructor //无参构造
@AllArgsConstructor //有参构造
public class User {

    private Integer id;
    private String name;
    private Short age;
    private Short gender;
    private String phone;

}