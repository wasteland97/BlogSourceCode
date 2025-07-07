package com.wasteland.blogsourcecode.provider;

import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.rpc.Protocol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
@author wfhstart
@create 2025-05-22 
*/
class UserServiceImplTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    public void test1() {
        ExtensionLoader<Protocol> extensionLoader =
                ExtensionLoader.getExtensionLoader(Protocol.class);
        Protocol redisProtocol = extensionLoader.getExtension("redis");
        int redisDefaultPort = redisProtocol.getDefaultPort();
        System.out.println("redis协议里的默认端口号是：" + redisDefaultPort);
        Protocol dubboProtocol = extensionLoader.getExtension("dubbo");
        int dubboDefaultPort = dubboProtocol.getDefaultPort();
        System.out.println("dubbo协议里的默认端口号是：" + dubboDefaultPort);
    }
}