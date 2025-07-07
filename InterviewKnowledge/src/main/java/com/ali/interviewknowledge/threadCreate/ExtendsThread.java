package com.ali.interviewknowledge.threadCreate;

/**
 * @author wfhstart
 * @create 2025-02-12
 */
public class ExtendsThread extends Thread{
    @Override
    public void run() {
        System.out.println("1...");
    }

    public static void main(String[] args) {
        ExtendsThread extendsThread = new ExtendsThread();
        extendsThread.start();
    }
}
