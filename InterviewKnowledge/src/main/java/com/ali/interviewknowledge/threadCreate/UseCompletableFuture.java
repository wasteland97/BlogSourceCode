package com.ali.interviewknowledge.threadCreate;

import java.util.concurrent.CompletableFuture;

/**
 * @author wfhstart
 * @create 2025-02-12
 */
public class UseCompletableFuture {
    public static void main(String[] args) throws InterruptedException {
        CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
            System.out.println("5....");
            return "2";
        });
        // 需要阻塞，否则看不到结果
        Thread.sleep(1000);
    }
}
