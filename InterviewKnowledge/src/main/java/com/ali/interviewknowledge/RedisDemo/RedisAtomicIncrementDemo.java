package com.ali.interviewknowledge.RedisDemo;

import org.redisson.Redisson;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author wfhstart
 * @create 2025-03-31
 */
public class RedisAtomicIncrementDemo {
    private static final String REDIS_KEY = "atomic:counter1";
    private static final int THREAD_COUNT = 10;
    private static final int OPERATIONS_PER_THREAD = 1;

    public static void main(String[] args) throws InterruptedException {
        // 1. 配置Redisson客户端
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://101.37.160.246:6379")
                .setPassword("ningzaichun");
        RedissonClient redisson = Redisson.create(config);

        // 2. 初始化Redis中的计数器
        RAtomicLong counter = redisson.getAtomicLong(REDIS_KEY);
        // counter.set(0L);

        // 3. 创建线程池
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);

        // 4. 提交任务
        for (int i = 0; i < THREAD_COUNT; i++) {
            executor.submit(new IncrementTask(redisson, i));
        }

        // 5. 关闭线程池并等待完成
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        // 6. 打印最终结果
        System.out.println("Final counter value: " + counter.get());

        // 7. 关闭Redisson客户端
        redisson.shutdown();
    }

    static class IncrementTask implements Runnable {
        private final RedissonClient redisson;
        private final int threadId;
        private final Random random = new Random();

        public IncrementTask(RedissonClient redisson, int threadId) {
            this.redisson = redisson;
            this.threadId = threadId;
        }

        @Override
        public void run() {
            RAtomicLong counter = redisson.getAtomicLong(REDIS_KEY);

            for (int i = 0; i < OPERATIONS_PER_THREAD; i++) {
                // 随机增量(1-10)
                int increment = random.nextInt(10) + 1;

                // 方法1: 使用原子操作（推荐）
                long newValue = counter.addAndGet(increment);

                // 方法2: 使用CAS操作（更细粒度控制）
                /*
                boolean success;
                do {
                    long current = counter.get();
                    long newVal = current + increment;
                    success = counter.compareAndSet(current, newVal);
                } while (!success);
                */

                System.out.printf("Thread-%d: Added %d, New value=%d%n",
                        threadId, increment, newValue);

                try {
                    // 随机休眠(0-50ms)模拟业务处理
                    Thread.sleep(random.nextInt(50));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }
}
