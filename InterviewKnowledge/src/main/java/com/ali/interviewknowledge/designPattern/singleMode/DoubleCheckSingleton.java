package com.ali.interviewknowledge.designPattern.singleMode;

/**
 * 双重校验锁实现单例
 * @author wfhstart
 * @create 2025-02-10
 */
public class DoubleCheckSingleton {
    private DoubleCheckSingleton() {

    }

    // 这里是防止指令重排序
    // 因为底下的instance = new DoubleCheckSingleton();其是分为3步：①为分配内存空间，②初始化，③将instance指向分配的内存地址
    private static volatile DoubleCheckSingleton instance;

    public static DoubleCheckSingleton getInstance() {
        // 判断对象是否已经实例化过，没有实例化才进入加锁代码
        if (instance == null) {
            // 类对象加锁
            synchronized (DoubleCheckSingleton.class) {
                if (instance == null) { // 可以避免重复创建对象
                    instance = new DoubleCheckSingleton();
                }
            }
        }
        return instance;
    }
}
