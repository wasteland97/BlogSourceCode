package com.ali.interviewknowledge.designPattern.singleMode;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author wfhstart
 * @create 2025-03-12
 */
public class AtomicSingleton {
    private static AtomicReference<AtomicSingleton> INSTANCE = new AtomicReference<>();

    private AtomicSingleton() {

    }


    public static AtomicSingleton getInstance() {
        AtomicSingleton instance = INSTANCE.get();
        if (instance == null) {
            instance = new AtomicSingleton();
            if (INSTANCE.compareAndSet(null, instance)) {
                return instance;
            } else {
                return INSTANCE.get();
            }
        }
        return instance;
    }

}
