package com.wasteland.blogsourcecode.debugDemo;

import org.assertj.core.util.Lists;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author wfhstart
 * @create 2025-07-03
 */
public class DebugCenter {

    private static int index;
    public static void main(String[] args) throws InterruptedException {
        testMultiThreadsDebug();
        testThrowException();
        int hitCount = 0;
        Student student = new Student();
        student.setAge(18);
        student.setName("张三");
        student.setAddress("浙江");
        Student student1 = new Student();
        student1.setAge(19);
        student1.setName("李四");
        student1.setAddress("李四");
        hitCount += 20;
        deleteData();
        List<Student> studentList = Lists.newArrayList(student, student1);
        int res = count(100);
        Integer testSetVarResult = testSetVar(res);
        List<String> nameList = studentList.stream().filter(Objects::nonNull)
                .filter(it -> it.getAge() > 18)
                .map(Student::getName)
                .collect(Collectors.toList());
        System.out.println(res);
        System.out.println(hitCount);
    }

    private static void deleteData() {
        System.out.println("准备要删除的数据！");
        System.out.println("模拟删除数据库数据!");
    }
    
    private static Integer testSetVar(int number) {
        Integer res = null;
        index++;
        if (number == 5) {
            System.out.println("testSetVar方法逻辑正确");
        }
        return res;
    }


    private static int count(int number) {
        int res = 0;
        for (int i = 0; i < number; i++) {
            if (i == 44) {
                res += i;
            }
        }
        return res;
    }

    private static void testThrowException() {
        System.out.println("调用用户中心接口超时！");
    }

//    public static void main(String[] args) throws InterruptedException {
//        testMultiThreadsDebug();
//    }

    private static void testMultiThreadsDebug() throws InterruptedException {
        new Thread(() -> {
            System.out.println("Thread1 start");
            System.out.println("Thread1 end");
        }, "thread1").start();

        new Thread(() -> {
            try {
                Thread.sleep(1000);  // 防止thread2在thread1触发断点前已经执行完毕
                System.out.println("Thread2 start");
                System.out.println("Thread2 end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "thread2").start();

        Thread.sleep(1000); // 防止main线程在thread1触发断点前已经执行完毕

        System.out.println("main thread end");
    }
}
