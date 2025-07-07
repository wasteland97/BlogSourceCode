package com.wasteland.blogsourcecode.jconsole;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

/**
 * @author wfhstart
 * @create 2025-03-15
 */
public class JMXRemoteExample {
    public static void main(String[] args) {
        try {
            // JMX 服务地址
            String jmxUrl = "service:jmx:rmi:///jndi/rmi://127.0.0.1:12345/jmxrmi";
            JMXServiceURL url = new JMXServiceURL(jmxUrl);

            // 创建 JMX 连接
            JMXConnector jmxConnector = JMXConnectorFactory.connect(url);
            MBeanServerConnection connection = jmxConnector.getMBeanServerConnection();

            // 获取运行时信息
//            System.out.println("MBean Count: " + connection.getMBeanInfo("com.wasteland.blogsourcecode.jconsole:type=MethodMonitor"));

            // 关闭连接
            jmxConnector.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
