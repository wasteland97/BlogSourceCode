package com.ali.alicloud;

import com.cloud.compute.cloudcomputestandard.CloudCompute;

/**
 * 阿里云计算服务
 * @author wfhstart
 * @create 2025-02-07
 */
public class AliCloudCompute implements CloudCompute {
    @Override
    public void compute() {
        System.out.println("阿里云提供特有云计算服务！");
    }
}
