package com.ali.interviewknowledge.ProduceAndConsume;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author wfhstart
 * @create 2025-02-19
 */
public class ProducerConsumerExample2 {
    private static final BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(10);

    static class Producer implements Runnable {
        @Override
        public void run() {
            int value = 0;
            while (true) {
                try {
                    queue.put(value);
                    System.out.println("生产者线程" + Thread.currentThread().getName() + "生产了" + value);
                    value++;
                    Thread.sleep(300);
                } catch (InterruptedException e){
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    static class Consumer implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    int value = queue.take();
                    System.out.println("消费者线程" + Thread.currentThread().getName() + "消费了" + value);
                    Thread.sleep(300);
                } catch (InterruptedException e){
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new Producer()).start();

        new Thread(new Consumer()).start();
    }
}
