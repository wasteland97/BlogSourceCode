package com.ali.interviewknowledge.concurrentDemo;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * A、B、C线程交替打印1-100（Reentrantlock + Condition的await和signal方法）
 * @author wfhstart
 * @create 2025-03-16
 */
public class AlternatePrinting1 {
    private static final int MAX_NUMBER = 100;
    private static int count = 1;
    private static final ReentrantLock lock = new ReentrantLock();
    private static final Condition conditionA = lock.newCondition();
    private static final Condition conditionB = lock.newCondition();
    private static final Condition conditionC = lock.newCondition();

    public static void main(String[] args) {
        Thread threadA = new Thread(new Printer(0, conditionA, conditionB), "Thread-A");
        Thread threadB = new Thread(new Printer(1, conditionB, conditionC), "Thread-B");
        Thread threadC = new Thread(new Printer(2, conditionC, conditionA), "Thread-C");

        threadA.start();
        threadB.start();
        threadC.start();
    }

    static class Printer implements Runnable {
        private final int threadId; // 线程编号（0, 1, 2）
        private final Condition currentCondition; // 当前线程的 Condition
        private final Condition nextCondition; // 下一个线程的 Condition

        public Printer(int threadId, Condition currentCondition, Condition nextCondition) {
            this.threadId = threadId;
            this.currentCondition = currentCondition;
            this.nextCondition = nextCondition;
        }

        @Override
        public void run() {
            while (true) {
                lock.lock();
                try {
                    // 检查是否超过最大值
                    if (count > MAX_NUMBER) {
                        break;
                    }
                    // 判断当前线程是否应该打印
                    if (count % 3 == threadId) {
                        System.out.println(Thread.currentThread().getName() + ": " + count);
                        count++;
                        nextCondition.signal(); // 唤醒下一个线程
                    } else {
                        currentCondition.await(); // 不符合条件，进入等待
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
            // 唤醒所有线程，确保程序退出
            lock.lock();
            try {
                nextCondition.signalAll();
            } finally {
                lock.unlock();
            }
        }
    }
}
