package com.ali.interviewknowledge.threadCreate;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @author wfhstart
 * @create 2025-02-12
 */
public class ImplementsCallable implements Callable<String> {
    @Override
    public String call() throws Exception {
        System.out.println("3...");
        return "";
    }
    public static void main(String[] args) {
        ImplementsCallable implementsCallable = new ImplementsCallable();
        FutureTask<String> stringFutureTask = new FutureTask<>(implementsCallable);
        new Thread(stringFutureTask).start();
    }
}
