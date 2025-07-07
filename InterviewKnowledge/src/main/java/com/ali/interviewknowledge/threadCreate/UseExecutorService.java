package com.ali.interviewknowledge.threadCreate;

import java.util.concurrent.*;

/**
 * @author wfhstart
 * @create 2025-02-12
 */
public class UseExecutorService {
    public static void main(String[] args) {
        ExecutorService poolA = Executors.newFixedThreadPool(2);
        poolA.execute(() -> {
            System.out.println("线程池A");
        });
        poolA.shutdown();

        ThreadPoolExecutor poolB = new ThreadPoolExecutor(2, 3, 0, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(3),
                Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
        poolB.submit(() -> {
            System.out.println("线程池B");
        });
        poolB.shutdown();
    }
}
