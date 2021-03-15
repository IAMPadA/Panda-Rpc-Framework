package com.panda.rpc.loadbalancer;

import com.alibaba.nacos.api.naming.pojo.Instance;

import java.util.List;

/**
 * @author [PANDA] 1843047930@qq.com
 * @date [2021-03-15 16:05]
 * @description 轮转算法,按顺序来
 */
public class RoundRobinLoadBalancer implements LoadBalancer{

    /**
     * index表示当前选到了第几个服务器，并且每次选择后都会自增一
     */
    private int index = 0;

    @Override
    public Instance select(List<Instance> instances) {
        if(index >= instances.size()){
            index %= instances.size();
        }
        return instances.get(index++);
    }
}
