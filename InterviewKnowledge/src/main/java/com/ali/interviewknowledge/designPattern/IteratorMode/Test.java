package com.ali.interviewknowledge.designPattern.IteratorMode;

import java.util.*;
import java.util.concurrent.CompletableFuture;

/**
 * @author wfhstart
 * @create 2025-02-20
 */
public class Test {
    private static final List<Integer> list = new ArrayList<>();
    public static void main(String[] args) {
        ClassList classList = new ClassList();
        classList.addStudent(new Student("张三", 18));
        classList.addStudent(new Student("李四", 19));
        classList.addStudent(new Student("王五", 20));

        // 获取迭代器，遍历学生信息
        StudentIterator studentIterator = classList.createIterator();
        while (studentIterator.hasNext()) {
            Student student = studentIterator.next();
            System.out.println("学生姓名：" + student.getName() + "，学生年龄：" + student.getAge());
        }

        Map<Integer, List<Integer>> dict = new HashMap<>();
        int key = Integer.MAX_VALUE;
        for (int i = 0; i < 100; i++) {
            dict.put(key, new ArrayList<>());
            dict.computeIfAbsent(--key, ArrayList::new);
        }
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("Task is running in " + Thread.currentThread().getName());
            return "Hello, World!";
        });

        Student a = new Student("张三", 18);
        String string = a.toString();
        System.out.println(string);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.delete(0, stringBuilder.length() - 1);

        list.add(2);
        Random random = new Random();
        random.nextInt();

    }
}
