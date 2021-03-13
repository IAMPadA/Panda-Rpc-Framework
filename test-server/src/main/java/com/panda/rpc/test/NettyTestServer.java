package com.panda.rpc.test;

import com.panda.rpc.api.HelloService;
import com.panda.rpc.serializer.ProtostuffSerializer;
import com.panda.rpc.transport.netty.server.NettyServer;

/**
 * @author [PANDA] 1843047930@qq.com
 * @date [2021-02-22 17:13]
 * @description 测试用Netty服务端
 */
public class NettyTestServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        NettyServer server = new NettyServer("127.0.0.1", 9999);
        server.setSerializer(new ProtostuffSerializer());
        server.publishService(helloService, HelloService.class);
    }
}
