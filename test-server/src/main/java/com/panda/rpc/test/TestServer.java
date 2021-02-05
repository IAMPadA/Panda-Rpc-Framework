package com.panda.rpc.test;

import com.panda.rpc.api.HelloService;
import com.panda.rpc.server.RpcServer;

/**
 * @author [PANDA] 1843047930@qq.com
 * @date [2021-02-05 14:45]
 * @description 测试用服务端
 */
public class TestServer {

    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        RpcServer rpcServer = new RpcServer();
        //注册HelloServiceImpl服务
        rpcServer.register(helloService, 9000);
    }
}
