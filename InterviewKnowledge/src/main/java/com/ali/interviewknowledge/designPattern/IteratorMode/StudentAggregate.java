package com.ali.interviewknowledge.designPattern.IteratorMode;

/**
 *  抽象聚合器（Aggregate）：学生聚合器
 *
 * @author wfhstart
 * @create 2025-02-20
 */
public interface StudentAggregate {
    // 用于创建具体的迭代器对象
    StudentIterator createIterator();

    void addStudent(Student student);
}
