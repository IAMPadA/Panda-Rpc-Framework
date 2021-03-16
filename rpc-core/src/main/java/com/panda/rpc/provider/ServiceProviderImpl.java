package com.panda.rpc.provider;

import com.panda.rpc.enumeration.RpcError;
import com.panda.rpc.exception.RpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author [PANDA] 1843047930@qq.com
 * @date [2021-02-07 17:14]
 * @description 默认的服务注册表，保存服务端本地服务
 */
public class ServiceProviderImpl implements ServiceProvider {

    private static final Logger logger = LoggerFactory.getLogger(ServiceProviderImpl.class);
    /**
     * key = 服务名称(即接口名), value = 服务实体(即实现类的实例对象)
     */
    private static final Map<String, Object> serviceMap = new ConcurrentHashMap<>();

    /**
     * 用来存放服务名称(即接口名）
     */
    private static final Set<String> registeredService = ConcurrentHashMap.newKeySet();

    /**
     * @description 保存服务到本地服务注册表
     * @param [service, serviceClass] 服务的实现类对象，服务类（接口）
     * @return [void]
     * @date [2021-03-14 9:51]
     */
    @Override
    public <T> void addServiceProvider(T service, String serviceName) {
        if(registeredService.contains(serviceName)){
            return;
        }
        registeredService.add(serviceName);
        serviceMap.put(serviceName, service);
        logger.info("向接口：{} 注册服务：{}", service.getClass().getInterfaces(), serviceName);
    }

    @Override
    public Object getServiceProvider(String serviceName) {
        Object service = serviceMap.get(serviceName);
        if(service == null){
            throw new RpcException(RpcError.SERVICE_NOT_FOUND);
        }
        return service;
    }
}
