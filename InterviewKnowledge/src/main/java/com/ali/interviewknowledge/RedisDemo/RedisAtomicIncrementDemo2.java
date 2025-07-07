package com.ali.interviewknowledge.RedisDemo;

import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;


import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author wfhstart
 * @create 2025-03-31
 */
public class RedisAtomicIncrementDemo2 {
    private static final String REDIS_KEY = "k1";
    private static final int THREAD_COUNT = 10;
    private static final int OPERATIONS_PER_THREAD = 1;

    // Lua脚本（使用KEYS[1]作为key，ARGV[1]作为增量）
    private static final String INCREMENT_SCRIPT =
            "local current = redis.call('GET', KEYS[1]) " +
                    "if current == false then " +
                    "   current = 0 " +
                    "else " +
                    "   current = tonumber(current) " +
                    "end " +
                    "local newValue = current + tonumber(ARGV[1]) " +
                    "redis.call('SET', KEYS[1], newValue) " +
                    "return newValue";

    public static void main(String[] args) throws InterruptedException {
//        原子性保证：
//            整个Lua脚本在Redis中作为一个原子操作执行
//            无需额外锁机制，避免竞争条件
//
//        高性能：
//            脚本在Redis服务器端执行，减少网络往返
//            预加载脚本(SHA1)后只需传输SHA1和参数
//
//        一致性：
//            GET和SET操作在同一个原子操作中完成
//            不会出现读取后值被其他客户端修改的问题
        // 1. 配置Redisson客户端
        Config config = new Config();
        config.setCodec(new JsonJacksonCodec()).useSingleServer()
                .setAddress("redis://101.37.160.246:6379")
                .setPassword("ningzaichun");

        RedissonClient redisson = Redisson.create(config);

        // 2. 初始化Redis中的计数器
//        redisson.getBucket(REDIS_KEY).set(0L);

        // 3. 预加载Lua脚本
        String sha1 = redisson.getScript().scriptLoad(INCREMENT_SCRIPT);


        // 4. 创建线程池
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);

        // 5. 提交任务
        for (int i = 0; i < THREAD_COUNT; i++) {
            executor.submit(new IncrementTask(redisson, i, sha1));
        }

        // 6. 关闭线程池并等待完成
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        // 7. 打印最终结果
        System.out.println("Final counter value: " + redisson.getBucket(REDIS_KEY).get());

        // 8. 关闭Redisson客户端
        redisson.shutdown();
    }

    static class IncrementTask implements Runnable {
        private final RedissonClient redisson;
        private final int threadId;
        private final String scriptSha1;
        private final Random random = new Random();

        public IncrementTask(RedissonClient redisson, int threadId, String scriptSha1) {
            this.redisson = redisson;
            this.threadId = threadId;
            this.scriptSha1 = scriptSha1;
        }

        @Override
        public void run() {
            RScript script = redisson.getScript();

            for (int i = 0; i < OPERATIONS_PER_THREAD; i++) {
                // 随机增量(1-10)
                int increment = random.nextInt(10) + 1;

                // 执行Lua脚本
                Long newValue = script.evalSha(RScript.Mode.READ_WRITE,
                        scriptSha1,
                        RScript.ReturnType.INTEGER,
                        Collections.singletonList(REDIS_KEY),
                        increment);

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
