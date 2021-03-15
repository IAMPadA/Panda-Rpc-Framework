package com.panda.rpc.loadbalancer;

import com.alibaba.nacos.api.naming.pojo.Instance;

import java.util.List;

/**
 * @author [PANDA] 1843047930@qq.com
 * @date [2021-03-15 15:53]
 * @description 负载均衡接口
 */
public interface LoadBalancer {

    /**
     * @description 从一系列Instance中选择一个
     * @param [instances]
     * @return [com.alibaba.nacos.api.naming.pojo.Instance]
     * @date [2021-03-15 16:00]
     */
    Instance select(List<Instance> instances);
    
}
