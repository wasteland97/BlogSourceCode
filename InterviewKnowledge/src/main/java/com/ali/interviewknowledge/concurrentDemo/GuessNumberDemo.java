package com.ali.interviewknowledge.concurrentDemo;

import java.util.Random;

/**
 *
 * 猜数字游戏：A线程出一个0-100的随机数，然后B线程和C线程轮流猜数，且必须等A线程先出完数字题
 * @author wfhstart
 * @create 2025-03-09
 */
public class GuessNumberDemo {

    private static int targetNumber = 0;

    private static volatile boolean isGuessed = false; // 是否猜中

    private static final Object lock = new Object(); // 用于线程间通信的锁对象


    public static void main(String[] args) {
        // 线程 A：生成随机数
        Thread threadA = new Thread(() -> {
            Random random = new Random();
            targetNumber = random.nextInt(101);
            System.out.println("Thread A generated the target number: " + targetNumber);
        });

        // 线程 B：猜测数字
        Thread threadB = new Thread(() -> {
            guessNumber("theadB");

        });

        // 线程 C：猜测数字
        Thread threadC = new Thread(() -> {
            guessNumber("theadC");
        });

        // 启动线程 A
        threadA.start();
        try {
            threadA.join();// 等待线程 A 完成，确保目标数字已生成
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 启动线程 B 和线程 C
        threadB.start();
        threadC.start();
    }

    private static void guessNumber(String threadName) {
        Random random = new Random();
        // 如果没有猜中，继续猜
        while (!isGuessed) {
            synchronized (lock) {
                if (isGuessed) {
                    break;
                }

                // 生成一个 0-100 的随机猜测数
                int guess = random.nextInt(101);
                System.out.println(threadName + " guesses: " + guess);

                if (guess == targetNumber) {
                    isGuessed = true; // 标记为猜中
                    System.out.println(threadName + " guessed the number: " + targetNumber);
                    lock.notifyAll(); // 唤醒其他线程
                } else {
                    // 唤醒另一个线程，自己进入等待状态
                    lock.notifyAll();
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
