package com.ali.interviewknowledge.designPattern.IteratorMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 具体聚合器（ConcreteAggregate）：班级列表
 * 实现抽象聚合器定义的接口，负责创建具体的迭代器对象，并返回该对象。
 *
 * @author wfhstart
 * @create 2025-02-20
 */
public class ClassList implements StudentAggregate{

    private List<Student> students = new ArrayList<>();

    // 创建迭代器对象
    @Override
    public StudentIterator createIterator() {
        return new StudentListIterator(students);
    }

    /**
     * 向班级名单中添加学生信息
     * @param student
     */
    @Override
    public void addStudent(Student student) {
        students.add(student);
    }
}
