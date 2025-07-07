package com.ali.interviewknowledge.concurrentDemo;

import java.util.Random;


/**
 * 题目：20个账户，每个账户初始1000，创建10个线程，每次挑取2个账户进行转账，转账金额为0-100以内的正整数，每个线程执行100次转账
 *
 * 实现方式：synchronized锁，要顺序加锁，避免死锁
 *
 * @author wfhstart
 * @create 2025-03-24
 */
public class BankTransferDemo1 {
    private static final int[] accounts = new int[20];
    private static final Object[] locks = new Object[20]; // 每个账户一个锁

    public static void main(String[] args) {
        // 初始化账户和锁
        for (int i = 0; i < 20; i++) {
            accounts[i] = 1000;
            locks[i] = new Object();
        }

        // 创建10个线程
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                Random random = new Random();
                for (int j = 0; j < 100; j++) {
                    int fromIndex = random.nextInt(20);
                    int toIndex = random.nextInt(20);
                    while (fromIndex == toIndex) {
                        toIndex = random.nextInt(20);
                    }
                    int amount = random.nextInt(100); // 0~99

                    // 按顺序加锁，避免死锁
                    int firstLock = Math.min(fromIndex, toIndex);
                    int secondLock = Math.max(fromIndex, toIndex);

                    synchronized (locks[firstLock]) {
                        synchronized (locks[secondLock]) {
                            if (accounts[fromIndex] >= amount) {
                                accounts[fromIndex] -= amount;
                                accounts[toIndex] += amount;
                            } else {
                                System.out.println("账户 " + fromIndex + " 余额不足");
                            }
                        }
                    }
                }
            }, "thread-" + i).start();
        }

        // 等待所有线程完成
        try {
            Thread.sleep(2000); // 简单等待
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 计算总金额
        int sum = 0;
        for (int i = 0; i < 20; i++) {
            System.out.println("账户 " + i + " 余额：" + accounts[i]);
            sum += accounts[i];
        }
        System.out.println("总金额：" + sum);
    }
}
