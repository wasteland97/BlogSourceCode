package com.ali.interviewknowledge.concurrentDemo;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;


/**
 * A线程、B线程、C线程，ABC线程顺序执行任务(CyclicBarrier类的await方法)
 * @author wfhstart
 * @create 2025-03-10
 */
public class SequentialExecuteDemo2 {
    public static void main(String[] args) {
        CyclicBarrier barrierAB = new CyclicBarrier(2); // A和B线程共享的屏障
        CyclicBarrier barrierBC = new CyclicBarrier(2); // B和C线程共享的屏障

        Thread threadA = new Thread(() -> {
            System.out.println("A线程开始执行");
            // A线程的任务
            try {
                barrierAB.await(); // A线程完成任务后释放屏障
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        });

        Thread threadB = new Thread(() -> {
            try {
                barrierAB.await(); // 等待A线程执行完毕
                System.out.println("B线程开始执行");
                // B线程的任务
                barrierBC.await(); // B线程完成任务后释放屏障
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        });

        Thread threadC = new Thread(() -> {
            try {
                barrierBC.await(); // 等待B线程执行完毕
                System.out.println("C线程开始执行");
                // C线程的任务
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        });

        threadA.start();
        threadB.start();
        threadC.start();
    }
}
