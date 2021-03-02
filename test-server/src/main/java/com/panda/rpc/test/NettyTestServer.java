package com.panda.rpc.test;

import com.panda.rpc.api.HelloService;
import com.panda.rpc.netty.server.NettyServer;
import com.panda.rpc.registry.DefaultServiceRegistry;
import com.panda.rpc.registry.ServiceRegistry;

/**
 * @author [PANDA] 1843047930@qq.com
 * @date [2021-02-22 17:13]
 * @description 测试用Netty服务端
 */
public class NettyTestServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        ServiceRegistry registry = new DefaultServiceRegistry();
        registry.register(helloService);
        NettyServer server = new NettyServer();
        server.start(9999);
    }
}
