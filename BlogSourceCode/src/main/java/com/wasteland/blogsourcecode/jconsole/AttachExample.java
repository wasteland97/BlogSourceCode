//package com.wasteland.blogsourcecode.jconsole;
//
//import com.sun.tools.attach.VirtualMachine;
//import com.sun.tools.attach.VirtualMachineDescriptor;
//
//import javax.management.MBeanServerConnection;
//import javax.management.remote.JMXConnector;
//import javax.management.remote.JMXConnectorFactory;
//import javax.management.remote.JMXServiceURL;
//import java.lang.management.ManagementFactory;
//import java.lang.management.ThreadInfo;
//import java.lang.management.ThreadMXBean;
//import java.util.List;
//
///**
// * @author wfhstart
// * @create 2025-03-15
// */
//public class AttachExample {
//    public static void main(String[] args) {
//        try {
//            // 获取本地 JVM 进程列表
//            List<VirtualMachineDescriptor> vmDescriptors = VirtualMachine.list();
//            String targetPid = "";
//            for (VirtualMachineDescriptor descriptor : vmDescriptors) {
//                System.out.println("PID: " + descriptor.id() + ", Name: " + descriptor.displayName());
//                if ("com.wasteland.blogsourcecode.jconsole.DeadLockDemo".equals(descriptor.displayName())) {
//                    // 找到目标进程，进行连接
//                    targetPid = descriptor.id();
//                    break;
//                }
//            }
//
//            VirtualMachine vm = VirtualMachine.attach(targetPid);
//
//            // 获取 JMX 连接地址
//            String connectorAddress = vm.getAgentProperties().getProperty("com.sun.management.jmxremote.localConnectorAddress");
//            if (connectorAddress == null) {
//                // 如果 JMX 代理未启动，则加载代理
//                String agentPath = vm.getSystemProperties().getProperty("java.home") +
//                        "/lib/management-agent.jar";
//                vm.loadAgent(agentPath);
//                connectorAddress = vm.getAgentProperties().getProperty("com.sun.management.jmxremote.localConnectorAddress");
//            }
//
//            System.out.println("JMX Connector Address: " + connectorAddress);
//
//            // 建立 JMX 连接
//            JMXServiceURL url = new JMXServiceURL(connectorAddress);
//            JMXConnector jmxConnector = JMXConnectorFactory.connect(url);
//            MBeanServerConnection connection = jmxConnector.getMBeanServerConnection();
//
////            // 获取 ThreadMXBean
////            ThreadMXBean threadMXBean = ManagementFactory.newPlatformMXBeanProxy(
////                    connection, ManagementFactory.THREAD_MXBEAN_NAME, ThreadMXBean.class);
////
////            // 检测死锁
////            long[] deadlockedThreads = threadMXBean.findDeadlockedThreads();
////            if (deadlockedThreads != null) {
////                System.out.println("Deadlock detected!");
////                for (long threadId : deadlockedThreads) {
////                    // 获取死锁的线程信息
////                    // 为了确保堆栈跟踪信息被打印完全不被截断，这里最好指定线程堆栈跟踪的深度
////                    ThreadInfo threadInfo = threadMXBean.getThreadInfo(threadId, Integer.MAX_VALUE);
////                    System.out.println("Deadlocked Thread: " + threadInfo.getThreadName());
////                    System.out.println("Stack Trace:");
////                    for (StackTraceElement element : threadInfo.getStackTrace()) {
////                        System.out.println("\t" + element.toString());
////                    }
////                }
////            } else {
////                System.out.println("No deadlock detected.");
////            }
//
//            // 断开连接
//            jmxConnector.close();
//            vm.detach();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
