package com.huawei.huaweicloud;

import com.cloud.compute.cloudcomputestandard.CloudCompute;

/**
 * @author wfhstart
 * @create 2025-02-07
 */
public class HuaWeiCloudCompute implements CloudCompute {

    @Override
    public void compute() {
        System.out.println("华为云提供特有云计算服务！");
    }
}
