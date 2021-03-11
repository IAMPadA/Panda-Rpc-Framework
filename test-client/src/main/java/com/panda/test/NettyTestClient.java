package com.panda.test;

import com.panda.rpc.RpcClient;
import com.panda.rpc.RpcClientProxy;
import com.panda.rpc.api.HelloObject;
import com.panda.rpc.api.HelloService;
import com.panda.rpc.netty.client.NettyClient;
import com.panda.rpc.serializer.ProtostuffSerializer;

/**
 * @author [PANDA] 1843047930@qq.com
 * @date [2021-02-22 17:17]
 * @description 测试用Netty客户端
 */
public class NettyTestClient {
    public static void main(String[] args) {
        RpcClient client = new NettyClient("127.0.0.1", 9999);
        client.setSerializer(new ProtostuffSerializer());
        RpcClientProxy rpcClientProxy = new RpcClientProxy(client);
        HelloService helloService = rpcClientProxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(12, "this is netty style");
        String res = helloService.hello(object);
        System.out.println(res);
    }
}
