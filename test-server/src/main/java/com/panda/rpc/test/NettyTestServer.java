package com.panda.rpc.test;

import com.panda.rpc.annotation.ServiceScan;
import com.panda.rpc.serializer.CommonSerializer;
import com.panda.rpc.transport.RpcServer;
import com.panda.rpc.transport.netty.server.NettyServer;

/**
 * @author [PANDA] 1843047930@qq.com
 * @date [2021-02-22 17:13]
 * @description 测试用Netty服务端
 */
@ServiceScan
public class NettyTestServer {
    public static void main(String[] args) {
        RpcServer server = new NettyServer("127.0.0.1", 9999, CommonSerializer.PROTOBUF_SERIALIZER);
        server.start();
    }
}
