package com.ali.interviewknowledge.concurrentDemo;


/**
 * @author wfhstart
 * @create 2025-03-10
 */
public class ThreadJoinDemo {
    public static void main(String[] args) {
        Thread threadA = new Thread(() -> {
            System.out.println("线程A执行完成");
        });
        Thread threadB = new Thread(() -> {
            System.out.println("线程B执行完成");
        });
        Thread threadC = new Thread(() -> {
            System.out.println("线程C执行完成");
        });
        threadA.start();
        threadB.start();
        threadC.start();
        try {
            threadA.join();
            threadB.join();
            threadC.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("我们打印完了！");
    }
}
