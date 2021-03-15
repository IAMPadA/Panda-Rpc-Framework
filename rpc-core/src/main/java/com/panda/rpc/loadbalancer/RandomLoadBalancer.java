package com.panda.rpc.loadbalancer;

import com.alibaba.nacos.api.naming.pojo.Instance;

import java.util.List;
import java.util.Random;

/**
 * @author [PANDA] 1843047930@qq.com
 * @date [2021-03-15 15:59]
 * @description 随机选择一个
 */
public class RandomLoadBalancer implements LoadBalancer{

    @Override
    public Instance select(List<Instance> instances) {
        return instances.get(new Random().nextInt(instances.size()));
    }
}
