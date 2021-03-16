package com.panda.rpc.register;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.panda.rpc.enumeration.RpcError;
import com.panda.rpc.exception.RpcException;
import com.panda.rpc.loadbalancer.LoadBalancer;
import com.panda.rpc.loadbalancer.RandomLoadBalancer;
import com.panda.rpc.util.NacosUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author [PANDA] 1843047930@qq.com
 * @date [2021-03-14 9:54]
 * @description 服务发现
 */
public class NacosServiceDiscovery implements ServiceDiscovery{

    private static final Logger logger = LoggerFactory.getLogger(NacosServiceDiscovery.class);

    private final LoadBalancer loadBalancer;

    public NacosServiceDiscovery(LoadBalancer loadBalancer){
        if (loadBalancer == null){
            this.loadBalancer = new RandomLoadBalancer();
        }else {
            this.loadBalancer = loadBalancer;
        }
    }
    /**
     * @description 根据服务名称从注册中心获取到一个服务提供者的地址
     * @param [serviceName]
     * @return [java.net.InetSocketAddress]
     * @date [2021-03-14 10:19]
     */
    @Override
    public InetSocketAddress lookupService(String serviceName) {
        try {
            //利用列表获取某个服务的所有提供者
            List<Instance> instances = NacosUtil.getAllInstance(serviceName);
            if(instances.size() == 0){
                logger.error("找不到对应服务：" + serviceName);
                throw new RpcException(RpcError.SERVICE_NOT_FOUND);
            }
            //负载均衡获取一个服务实体
            Instance instance = loadBalancer.select(instances);
            return new InetSocketAddress(instance.getIp(), instance.getPort());
        }catch (NacosException e) {
            logger.error("获取服务时有错误发生", e);
        }
        return null;
    }
}
