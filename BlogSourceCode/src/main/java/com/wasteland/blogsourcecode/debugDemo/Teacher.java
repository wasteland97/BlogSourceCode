package com.wasteland.blogsourcecode.debugDemo;

import lombok.experimental.Accessors;
import lombok.Data;

/**
 * @author wfhstart
 * @create 2025-07-04
 */
@Data
@Accessors(chain = true)
public class Teacher {

    private String name;

    private Car car;


    @Data
    @Accessors(chain = true)
    public static class Car {

        private String brand;

        private Double price;

    }
}