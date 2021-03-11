package com.panda.rpc;

import com.panda.rpc.serializer.CommonSerializer;

/**
 * @author [PANDA] 1843047930@qq.com
 * @date [2021-02-18 17:53]
 * @description 服务端类通过接口
 */
public interface RpcServer {

    void start(int port);

    void setSerializer(CommonSerializer serializer);
}
