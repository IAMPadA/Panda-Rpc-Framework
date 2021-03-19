package com.panda.rpc.transport.netty.client;

import com.panda.rpc.entity.RpcResponse;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author [PANDA] 1843047930@qq.com
 * @date [2021-03-15 14:18]
 * @description 未处理的请求（对所有Netty客户端请求进行统一管理）
 */
public class UnprocessedRequests {

    private static ConcurrentHashMap<String, CompletableFuture<RpcResponse>> unprocessedRequests = new ConcurrentHashMap<>();

    public void put(String requestId, CompletableFuture<RpcResponse> future) {
        unprocessedRequests.put(requestId, future);
    }

    public void remove(String requestId) {
        unprocessedRequests.remove(requestId);
    }

    public void complete(RpcResponse rpcResponse){
        //请求完成了，把请求从未完成的请求中移除
        CompletableFuture<RpcResponse> future = unprocessedRequests.remove(rpcResponse.getRequestId());
        if(null != future){
            //把响应对象放入future中
            future.complete(rpcResponse);
        }else {
            throw new IllegalStateException();
        }
    }
}
