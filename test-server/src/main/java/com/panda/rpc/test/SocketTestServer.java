package com.panda.rpc.test;

import com.panda.rpc.api.HelloService;
import com.panda.rpc.registry.DefaultServiceRegistry;
import com.panda.rpc.registry.ServiceRegistry;
import com.panda.rpc.socket.server.SocketServer;

/**
 * @author [PANDA] 1843047930@qq.com
 * @date [2021-02-05 14:45]
 * @description 测试用服务端
 */
public class SocketTestServer {

    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        ServiceRegistry serviceRegistry = new DefaultServiceRegistry();
        serviceRegistry.register(helloService);
        SocketServer socketServer = new SocketServer(serviceRegistry);
        socketServer.start(9000);
    }
}
