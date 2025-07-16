package com.wasteland.blogsourcecode.AiCodeReview;

import com.wasteland.blogsourcecode.debugDemo.Student;
import org.assertj.core.util.Lists;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author wfhstart
 * @create 2025-07-08
 */
public class TestAi {
    public static void main(String[] args) {
        Student student = new Student();
        student.setAge(18);
        student.setName("张三");
        student.setAddress("浙江");
        Student student1 = new Student();
        student1.setAge(19);
        student1.setName("李四");
        student1.setAddress("湖北");

        List<Student> studentList = Lists.newArrayList(student, student1);

        List<String> nameList = studentList.stream().filter(Objects::nonNull)
                .filter(it -> it.getAge() > 18)
                .map(Student::getName)
                .collect(Collectors.toList());
        System.out.println(nameList);

        int count = count(55);
        System.out.println(count);
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

    private static int count1(int number) {
        int res = 0;
        for (int i = 0; i < number; i++) {
            if (i == 44) {
                res += i;
            }
        }
        return res;
    }

    private static int count2(int number) {
        int res = 0;
        for (int i = 0; i < number; i++) {
            if (i == 44) {
                res += i;
            }
        }
        return res;
    }

    private static int count3(int number) {
        int res = 0;
        for (int i = 0; i < number; i++) {
            if (i == 44) {
                res += i;
            }
        }
        return res;
    }

    private static int count4(int number) {
        int res = 0;
        for (int i = 0; i < number; i++) {
            if (i == 44) {
                res += i;
            }
        }
        return res;
    }

    private static int count5(int number) {
        int res = 0;
        for (int i = 0; i < number; i++) {
            if (i == 44) {
                res += i;
            }
        }
        return res;
    }
}
