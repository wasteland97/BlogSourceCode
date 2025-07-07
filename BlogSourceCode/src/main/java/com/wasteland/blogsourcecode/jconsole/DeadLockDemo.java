package com.wasteland.blogsourcecode.jconsole;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

/**
 * @author wfhstart
 * @create 2025-03-15
 */
public class DeadLockDemo {
    private static Object resource1 = new Object();
    private static Object resource2 = new Object();

    private static volatile MethodMonitor methodMonitor;
    public static MethodMonitor getInstance() {
        // 判断对象是否已经实例化过，没有实例化才进入加锁代码
        if (methodMonitor == null) {
            // 类对象加锁
            synchronized (DeadLockDemo.class) {
                if (methodMonitor == null) { // 可以避免重复创建对象
                    methodMonitor = new MethodMonitor();
                }
            }
        }
        return methodMonitor;
    }

    public static void main(String[] args) throws Exception {
        // 获取 MBean 服务器
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

        // 创建 MBean 实例
        MethodMonitor methodMonitor = DeadLockDemo.getInstance();

        // 注册 MBean
        ObjectName objectName = new ObjectName("com.wasteland.blogsourcecode.jconsole:type=MethodMonitor");
        mBeanServer.registerMBean(methodMonitor, objectName);

        new Thread(() -> {
            synchronized (resource1) {
                System.out.println(Thread.currentThread() + "get resource1");
                try {
                    int result = cacluate(4, 5);
                    System.out.println("thread2 calculate 4 + 5 result is : " + result);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread() + "waiting get resource2");
                synchronized (resource2) {
                    System.out.println(Thread.currentThread() + "get resource2");
                }
            }
        }, "thread1").start();

        new Thread(() -> {
            synchronized (resource2) {
                System.out.println(Thread.currentThread() + "get resource2");
                try {
                    int result = cacluate(1, 3);
                    System.out.println("thread2 calculate 1 + 3 result is : " + result);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread() + "waiting get resource1");
                synchronized (resource1) {
                    System.out.println(Thread.currentThread() + "get resource1");
                }
            }
        }, "thread2").start();

    }

    private static int cacluate(int a, int b) {
        methodMonitor.monitoredMethod();
        return a + b;
    }

}
