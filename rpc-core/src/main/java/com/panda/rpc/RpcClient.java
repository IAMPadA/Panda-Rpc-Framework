package com.panda.rpc;

import com.panda.rpc.entity.RpcRequest;

/**
 * @author [PANDA] 1843047930@qq.com
 * @date [2021-02-18 17:50]
 * @description 客户端类通用接口
 */
public interface RpcClient {

    Object sendRequest(RpcRequest rpcRequest);

}

