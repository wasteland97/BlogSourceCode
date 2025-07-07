package com.ali.interviewknowledge.concurrentDemo;

import java.util.concurrent.CountDownLatch;

/**
 *
 * A线程、B线程、C线程，ABC线程顺序执行任务（CountDownLatch的countDown、await方法）
 * @author wfhstart
 * @create 2025-03-09
 */
public class SequentialExecuteDemo {
    public static void main(String[] args) {
        CountDownLatch latch1 = new CountDownLatch(1);
        CountDownLatch latch2 = new CountDownLatch(1);

        Thread threadA = new Thread(() -> {
            System.out.println("A线程开始执行");
            // A线程的任务
            latch1.countDown(); // A线程执行完毕后，释放latch1
        });

        Thread threadB = new Thread(() -> {
            try {
                latch1.await(); // 等待A线程执行完毕
                System.out.println("B线程开始执行");
                // B线程的任务
                latch2.countDown(); // B线程执行完毕后，释放latch2
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread threadC = new Thread(() -> {
            try {
                latch2.await(); // 等待B线程执行完毕
                System.out.println("C线程开始执行");
                // C线程的任务
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        threadA.start();
        threadB.start();
        threadC.start();
    }
}
