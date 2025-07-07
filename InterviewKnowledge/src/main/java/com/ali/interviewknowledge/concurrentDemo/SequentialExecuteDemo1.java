package com.ali.interviewknowledge.concurrentDemo;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * A线程、B线程、C线程，ABC线程顺序执行任务（ReentrantLock + Condition的await和signal方法）
 * @author wfhstart
 * @create 2025-03-09
 */
public class SequentialExecuteDemo1 {
    private static final Lock lock = new ReentrantLock();
    private static final Condition conditionA = lock.newCondition();
    private static final Condition conditionB = lock.newCondition();
    private static final Condition conditionC = lock.newCondition();
    private static int currentThread = 1; // 1: A, 2: B, 3: C

    public static void main(String[] args) {
        Thread threadA = new Thread(() -> {
            lock.lock();
            try {
                while (currentThread != 1) {
                    conditionA.await();
                }
                System.out.println("A线程执行任务 ");
                currentThread = 2;
                conditionB.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });

        Thread threadB = new Thread(() -> {
            lock.lock();
            try {
                while (currentThread != 2) {
                    conditionB.await();
                }
                System.out.println("B线程执行任务 ");
                currentThread = 3;
                conditionC.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });

        Thread threadC = new Thread(() -> {
            lock.lock();
            try {
                while (currentThread != 3) {
                    conditionC.await();
                }
                System.out.println("C线程执行任务 ");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });

        threadA.start();
        threadB.start();
        threadC.start();
    }
}
