package com.ali.interviewknowledge.threadCreate;

/**
 * @author wfhstart
 * @create 2025-02-12
 */
public class ImplementsRunnable implements Runnable{
    @Override
    public void run() {
        System.out.println("2...");
    }

    public static void main(String[] args) {
        ImplementsRunnable implementsRunnable = new ImplementsRunnable();
        new Thread(implementsRunnable).start();
    }
}
