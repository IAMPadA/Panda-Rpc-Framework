package com.panda.rpc.test;

import com.panda.rpc.api.HelloService;
import com.panda.rpc.registry.DefaultServiceRegistry;
import com.panda.rpc.registry.ServiceRegistry;
import com.panda.rpc.server.RpcServer;

/**
 * @author [PANDA] 1843047930@qq.com
 * @date [2021-02-05 14:45]
 * @description 测试用服务端
 */
public class TestServer {

    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        ServiceRegistry serviceRegistry = new DefaultServiceRegistry();
        serviceRegistry.register(helloService);
        RpcServer rpcServer = new RpcServer(serviceRegistry);
        rpcServer.start(9000);
    }
}
