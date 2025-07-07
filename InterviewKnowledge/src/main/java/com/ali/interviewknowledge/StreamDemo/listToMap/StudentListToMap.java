package com.ali.interviewknowledge.StreamDemo.listToMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author wfhstart
 * @create 2025-03-10
 */
public class StudentListToMap {
    public static void main(String[] args) {
        // 创建学生列表
        List<Student> students = Arrays.asList(
                new Student("Alice", 20),
                new Student("Bob", 22),
                new Student("Charlie", 21)
        );

        students.stream()
                .collect(java.util.stream.Collectors.toMap(Student::getName, Student::getAge))
                .forEach((name, age) -> System.out.println("Name: " + name + ", Age: " + age));
        

        // 如果存在重复键，则会抛出异常，可以使用mergeFunction参数来解决这个问题。
        students.stream()
                .collect(java.util.stream.Collectors.toMap(Student::getName, Student::getAge, (oldValue, newValue) -> oldValue))
                .forEach((name, age) -> System.out.println("Name: " + name + ", Age: " + age));
    }
}
