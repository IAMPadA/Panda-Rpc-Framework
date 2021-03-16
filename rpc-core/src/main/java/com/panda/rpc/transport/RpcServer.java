package com.panda.rpc.transport;

import com.panda.rpc.serializer.CommonSerializer;

/**
 * @author [PANDA] 1843047930@qq.com
 * @date [2021-02-18 17:53]
 * @description 服务端类通过接口
 */
public interface RpcServer {

    int DEFAULT_SERIALIZER = CommonSerializer.KRYO_SERIALIZER;

    void start();

    /**
     * @description 向Nacos注册服务
     * @param [service, serviceClass]
     * @return [void]
     * @date [2021-03-13 15:56]
     */
    <T> void publishService(T service, String serviceName);
}
