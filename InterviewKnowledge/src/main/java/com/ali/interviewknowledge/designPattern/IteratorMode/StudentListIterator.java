package com.ali.interviewknowledge.designPattern.IteratorMode;

import java.util.List;

/**
 * @author wfhstart
 * @create 2025-02-20
 */
public class StudentListIterator implements StudentIterator{

    private List<Student> students;

    private int index;

    public StudentListIterator(List<Student> students) {
        this.students = students;
        this.index = 0;
    }

    /**
     * 判断是否还有下一个元素
     *
     * @return
     */
    @Override
    public boolean hasNext() {
        return index < students.size();
    }

    /**
     * 获取下一个元素
     *
     * @return
     */
    @Override
    public Student next() {
        if (!hasNext()) {
            throw new RuntimeException("没有更多元素");
        }
        Student student = students.get(index);
        index++;
        return student;
    }
}
