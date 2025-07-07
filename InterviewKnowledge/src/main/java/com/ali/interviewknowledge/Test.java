package com.ali.interviewknowledge;

import java.io.BufferedReader;
import java.io.File;
import java.io.FilterReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.Buffer;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author wfhstart
 * @create 2025-02-19
 */
public class Test {
    public static void main(String[] args) {
//        CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
//            System.out.println("5....");
//            return "2";
//        });
//        Set<String> set = new HashSet<>();
//        ArrayList<String> list = new ArrayList<>();
//        Thread thread = new Thread(() -> {
//            System.out.println("1....");
//        });
//        Set<String> set1 =new HashSet<>(new ArrayList<>());
//        Iterator<String> iterator = list.iterator();
//        String next = iterator.next();
//        while (iterator.hasNext()){
//            System.out.println(iterator.next());
//        }
//        List<Integer> list = new ArrayList<>();
//        Map<String,Integer> map = new HashMap<>();
//        list.add(1);
//        Integer a1 = 123;
//        Integer a2 = 123;
//        System.out.println(a1 == a2);
//        BigDecimal a = new BigDecimal("0.01");
//        BigDecimal b = new BigDecimal("0.010");
//        System.out.println(a.equals(b));
//        File file = new File("C:\\Users\\wfhstart\\Desktop\\test.txt");
//
//        ThreadLocal<Integer> threadLocal = new ThreadLocal<>();
//        System.out.println(threadLocal.get());
//        new ThreadPoolExecutor(5,10,1000,null,null);
//        Set<Integer> set = new HashSet<>();
        String res = reverseWords("a good   example");
        System.out.println(res);
    }

    public static String reverseWords(String s) {
        String[] splitString = s.split(" ");
        StringBuilder sb = new StringBuilder();
        for (int i = splitString.length - 1; i >= 0; i--) {
            if (!" ".equals(splitString[i])) {
                sb.append(splitString[i] + " ");
            }
        }
        String result = sb.toString();
        List<Integer> list = new ArrayList<>();
        Map<Integer, Integer> map = new HashMap<>();
        return result.trim();
    }
}
