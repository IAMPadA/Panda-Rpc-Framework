package com.panda.rpc.transport;

import com.panda.rpc.entity.RpcRequest;
import com.panda.rpc.entity.RpcResponse;
import com.panda.rpc.transport.netty.client.NettyClient;
import com.panda.rpc.transport.socket.client.SocketClient;
import com.panda.rpc.util.RpcMessageChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * @author [PANDA] 1843047930@qq.com
 * @date [2021-02-04 16:46]
 * @description Rpc客户端动态代理
 */
public class RpcClientProxy implements InvocationHandler {

    private static final Logger logger = LoggerFactory.getLogger(RpcClientProxy.class);
    private final RpcClient client;

    public RpcClientProxy(RpcClient client) {
        this.client = client;
    }

    //抑制编译器产生警告信息
    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> clazz){
        //创建代理对象
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, this);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        logger.info("调用方法：{}#{}", method.getDeclaringClass().getName(), method.getName());
        RpcRequest rpcRequest = new RpcRequest(UUID.randomUUID().toString(), method.getDeclaringClass().getName(),
                method.getName(), args, method.getParameterTypes(), false);
        RpcResponse rpcResponse = null;
        if(client instanceof NettyClient){
            try {
                //异步获取调用结果
                CompletableFuture<RpcResponse> completableFuture = (CompletableFuture<RpcResponse>)client.sendRequest(rpcRequest);
                rpcResponse = completableFuture.get();
            }catch (Exception e){
                logger.error("方法调用请求发送失败", e);
                return null;
            }
        }
        if(client instanceof SocketClient){
            rpcResponse = (RpcResponse) client.sendRequest(rpcRequest);
        }
        RpcMessageChecker.check(rpcRequest, rpcResponse);
        return rpcResponse.getData();
    }
}
