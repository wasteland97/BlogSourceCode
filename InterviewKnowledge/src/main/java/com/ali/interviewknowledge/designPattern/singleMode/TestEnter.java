package com.ali.interviewknowledge.designPattern.singleMode;


import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wfhstart
 * @create 2025-02-10
 */
public class TestEnter {
    public static void main(String[] args) {
        InnerClassSingleton instance1 = InnerClassSingleton.getInstance();
        InnerClassSingleton instance2 = InnerClassSingleton.getInstance();
        System.out.println(instance2 == instance1);
        System.out.println(instance1.equals(instance2));

        DoubleCheckSingleton instance3 = DoubleCheckSingleton.getInstance();
        DoubleCheckSingleton instance4 = DoubleCheckSingleton.getInstance();
        System.out.println(instance3 == instance4);

        EnumSingleton instance5 = EnumSingleton.INSTANCE;
        EnumSingleton instance6 = EnumSingleton.INSTANCE;
        System.out.println(instance5 == instance6);

        AtomicSingleton instance7 = AtomicSingleton.getInstance();
        AtomicSingleton instance8 = AtomicSingleton.getInstance();
        System.out.println(instance7 == instance8);

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                System.out.println(AtomicSingleton.getInstance());
            }).start();
        }
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
    }
}
