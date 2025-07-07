package com.ali.interviewknowledge.ProduceAndConsume;

import java.util.LinkedList;
import java.util.Queue;

/**
 * synchronized + Object类的wait、notify方法方实现生产者消费者模式
 * @author wfhstart
 * @create 2025-02-19
 */
public class ProducerConsumerExample3 {
    private static final int CAPACITY = 10;

    private static final Queue<Integer> QUEUE = new LinkedList<>();


    static class Producer implements Runnable {
        @Override
        public void run() {
            int value = 0;
            while (true) {
                synchronized (this) {
                    try {
                        while (QUEUE.size() == CAPACITY) {
                            System.out.println("队列已满，生产者线程" + Thread.currentThread().getName() + "进入等待状态");
                            QUEUE.wait();
                        }

                        QUEUE.add(value);
                        System.out.println("生产者线程" + Thread.currentThread().getName() + "生产了" + value);
                        value++;
                        notify(); // 通知消费者

                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
    }

    static class Consumer implements Runnable {
        @Override
        public void run() {
            while (true) {
                synchronized (this) {
                    try {
                        while (QUEUE.isEmpty()) {
                            System.out.println("队列已空，消费者线程" + Thread.currentThread().getName() + "进入等待状态");
                            QUEUE.wait();
                        }
                        int value = QUEUE.poll();
                        System.out.println("消费者线程" + Thread.currentThread().getName() + "消费了" + value);
                        notify();
                        Thread.sleep(300);

                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new Producer()).start();

        new Thread(new Consumer()).start();
    }
}
