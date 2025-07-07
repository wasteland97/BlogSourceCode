package com.cloud.compute.cloudcomputestandard;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ServiceLoader;

/**
 * 加载具体的服务实现
 * @author wfhstart
 * @create 2025-02-06
 */
public class CloudComputeService {
    private static volatile CloudComputeService instance;

    private final CloudCompute cloudCompute;

    private final List<CloudCompute> cloudComputes;

    /**
     * 加载服务（这里简单的直接使用JDK原生的ServiceLoader类）
     * */
    private CloudComputeService() {
        ServiceLoader<CloudCompute> loader = ServiceLoader.load(CloudCompute.class);
        List<CloudCompute> list = new ArrayList<>();
        for (CloudCompute cloudCompute : loader) {
            list.add(cloudCompute);
        }
        cloudComputes = list;
        if (!list.isEmpty()) {
            // 取第一个
            cloudCompute = list.get(0);
        } else {
            cloudCompute = null;
        }
    }


    /**
     * CloudComputeService 双重检验锁单例加载
     * */
    public static CloudComputeService getInstance() {
        if (instance == null) {
            synchronized (CloudComputeService.class) {
                if (instance == null) {
                    instance = new CloudComputeService();
                }
            }
        }
        return instance;
    }


    public void compute(){
        if(cloudComputes.isEmpty()){
            System.out.println("CloudCompute服务未加载！");
        }else {
            CloudCompute cloudCompute = cloudComputes.get(0);
            cloudCompute.compute();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CloudComputeService that = (CloudComputeService) o;
        return Objects.equals(cloudCompute, that.cloudCompute) && Objects.equals(cloudComputes, that.cloudComputes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cloudCompute, cloudComputes);
    }
}
