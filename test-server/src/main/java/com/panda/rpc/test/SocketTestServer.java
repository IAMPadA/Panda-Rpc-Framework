package com.panda.rpc.test;

import com.panda.rpc.api.HelloService;
import com.panda.rpc.serializer.CommonSerializer;
import com.panda.rpc.transport.socket.server.SocketServer;

/**
 * @author [PANDA] 1843047930@qq.com
 * @date [2021-02-05 14:45]
 * @description 测试用服务端
 */
public class SocketTestServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl2();
        SocketServer socketServer = new SocketServer("127.0.0.1", 9998, CommonSerializer.HESSIAN_SERIALIZER);
        socketServer.publishService(helloService, HelloService.class);
    }
}
