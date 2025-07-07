package com.ali.interviewknowledge.designPattern.singleMode;

/**
 * 枚举单例是《Effective Java》推荐的单例实现方式，利用枚举的特性保证线程安全和绝对单例。
 * 线程安全，由 JVM 保证枚举实例的唯一性
 * 枚举实现单例
 * @author wfhstart
 * @create 2025-02-10
 */
public enum EnumSingleton {
    INSTANCE;
}
