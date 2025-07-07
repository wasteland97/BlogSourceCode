package com.ali.interviewknowledge.concurrentDemo;

/**
 * A、B、C线程交替打印1-100（synchronized + wait + notifyAll）
 * @author wfhstart
 * @create 2025-03-16
 */
public class AlternatePrinting {
    private static final int MAX_NUMBER = 100;
    private static int count = 1;
    private static final Object lock = new Object();

    public static void main(String[] args) {
        Thread threadA = new Thread(new Printer(0), "Thread-A");
        Thread threadB = new Thread(new Printer(1), "Thread-B");
        Thread threadC = new Thread(new Printer(2), "Thread-C");

        threadA.start();
        threadB.start();
        threadC.start();
    }

    static class Printer implements Runnable {
        private final int threadId; // 线程编号（0, 1, 2）

        public Printer(int threadId) {
            this.threadId = threadId;
        }

        @Override
        public void run() {
            while (true) {
                synchronized (lock) {
                    // 检查是否超过最大值
                    if (count > MAX_NUMBER) {
                        break;
                    }
                    // 判断当前线程是否应该打印
                    if (count % 3 == threadId) {
                        System.out.println(Thread.currentThread().getName() + ": " + count);
                        count++;
                        lock.notifyAll(); // 唤醒其他线程
                    } else {
                        try {
                            lock.wait(); // 不符合条件，进入等待
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
