package com.ali.interviewknowledge.ProduceAndConsume;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock + Condition类的await和signal方法 实现生产者消费者模式 （也是LinkedBlockingQueue源码的实现方式）
 * @author wfhstart
 * @create 2025-02-19
 */
public class ProducerConsumerExample {
    private static final int CAPACITY = 10;
    private final Queue<Integer> queue = new LinkedList<>();
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    public void produce() throws InterruptedException {
        int value = 0;
        while (true) {
            lock.lock();
            try {
                while (queue.size() == CAPACITY) {
                    notFull.await();  // 队列满，等待
                }
                queue.add(value);
                System.out.println("Produced: " + value);
                value++;
                notEmpty.signal();  // 通知消费者
            } finally {
                lock.unlock();
            }
        }
    }

    public void consume() throws InterruptedException {
        while (true) {
            lock.lock();
            try {
                while (queue.isEmpty()) {
                    notEmpty.await();  // 队列空，等待
                }
                int value = queue.poll();
                System.out.println("Consumed: " + value);
                notFull.signal();  // 通知生产者
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        ProducerConsumerExample example = new ProducerConsumerExample();

        Thread producer = new Thread(() -> {
            try {
                example.produce();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                example.consume();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer.start();
    }
}
