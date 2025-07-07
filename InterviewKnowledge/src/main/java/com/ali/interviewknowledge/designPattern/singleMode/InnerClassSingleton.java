package com.ali.interviewknowledge.designPattern.singleMode;

/**
 * 内部类实现单例
 * 静态内部类单例模式中实例由内部类创建，由于JVM在加载外部类的过程中，是不会加载静态内部类的
 * 只有内部类的属性和方法被调用时才会被加载，并初始化静态属性。静态属性由于被static修饰，
 * 保证只被实例化一次，并且被严格保证实例化顺序
 * @author wfhstart
 * @create 2025-02-10
 */
public class InnerClassSingleton {
    private InnerClassSingleton(){}

    private static class SingletonHolder{
        private static final InnerClassSingleton INSTANCE = new InnerClassSingleton();
    }

    public static InnerClassSingleton getInstance(){
        return SingletonHolder.INSTANCE;
    }
}
