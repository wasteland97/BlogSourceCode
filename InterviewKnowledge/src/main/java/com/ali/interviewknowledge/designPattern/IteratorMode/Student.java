package com.ali.interviewknowledge.designPattern.IteratorMode;

//import com.wasteland.customprocessor.annotation.ToString;
import lombok.Data;
import lombok.ToString;
/**
 * 学生实体类
 *
 * @author wasteland
 * @create 2025-02-20
 */
@Data
public class Student {
    private String name;

    private Integer age;

    public Student(String name, Integer age)
    {
        this.name = name;
        this.age = age;
    }

    public static void main(String[] args) {
        Student student = new Student("wasteland", 18);
        System.out.println(student);
    }

}
