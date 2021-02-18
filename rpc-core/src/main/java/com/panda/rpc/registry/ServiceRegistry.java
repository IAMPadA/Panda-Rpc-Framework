package com.panda.rpc.registry;

/**
 * @author [PANDA] 1843047930@qq.com
 * @date [2021-02-07 16:55]
 * @description 服务注册表通用接口
 */
public interface ServiceRegistry {

    /**
     * @description 将一个服务注册进注册表
     * @param [service] 待注册的服务实体
     * @param <T> 服务实体类
     * @return [void]
     * @date [2021-02-07 16:59]
     */
    <T> void register(T service);
    
    /**
     * @description 根据服务名获取服务实体
     * @param [serviceName] 服务名称
     * @return [java.lang.Object] 服务实体
     * @date [2021-02-07 17:06]
     */
    Object getService(String serviceName);

}
