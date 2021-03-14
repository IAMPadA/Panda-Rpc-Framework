package com.panda.rpc.register;

import java.net.InetSocketAddress;

/**
 * @author [PANDA] 1843047930@qq.com
 * @date [2021-03-14 9:56]
 * @description 服务发现接口
 */
public interface ServiceDiscovery {
    /**
     * @description 根据服务名称查找服务端地址
     * @param [serviceName]
     * @return [java.net.InetSocketAddress]
     * @date [2021-03-14 10:09]
     */
    InetSocketAddress lookupService(String serviceName);
}
