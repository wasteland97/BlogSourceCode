package com.ali.interviewknowledge.designPattern.singleMode;

/**
 * @author wfhstart
 * @create 2025-03-12
 */
public class NotSafeLazySingleton {
    private static NotSafeLazySingleton instance;

    private NotSafeLazySingleton(){

    }

    public static NotSafeLazySingleton getInstance(){
        if(instance == null){
            instance = new NotSafeLazySingleton();
        }
        return instance;
    }
}
