package com.panda.rpc.test;

import com.panda.rpc.annotation.ServiceScan;
import com.panda.rpc.serializer.CommonSerializer;
import com.panda.rpc.transport.RpcServer;
import com.panda.rpc.transport.socket.server.SocketServer;

/**
 * @author [PANDA] 1843047930@qq.com
 * @date [2021-02-05 14:45]
 * @description 测试用服务端
 */
@ServiceScan
public class SocketTestServer {
    public static void main(String[] args) {
        RpcServer server = new SocketServer("127.0.0.1", 9998, CommonSerializer.HESSIAN_SERIALIZER);
        server.start();
    }
}
