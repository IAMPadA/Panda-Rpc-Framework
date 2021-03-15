package com.panda.rpc.transport.socket.client;

import com.panda.rpc.entity.RpcRequest;
import com.panda.rpc.entity.RpcResponse;
import com.panda.rpc.enumeration.RpcError;
import com.panda.rpc.exception.RpcException;
import com.panda.rpc.loadbalancer.LoadBalancer;
import com.panda.rpc.loadbalancer.RandomLoadBalancer;
import com.panda.rpc.register.NacosServiceDiscovery;
import com.panda.rpc.register.ServiceDiscovery;
import com.panda.rpc.serializer.CommonSerializer;
import com.panda.rpc.transport.RpcClient;
import com.panda.rpc.transport.socket.util.ObjectReader;
import com.panda.rpc.transport.socket.util.ObjectWriter;
import com.panda.rpc.util.RpcMessageChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author [PANDA] 1843047930@qq.com
 * @date [2021-02-04 17:20]
 * @description Socket方式进行远程调用的客户端
 */
public class SocketClient implements RpcClient {

    private static final Logger logger = LoggerFactory.getLogger(SocketClient.class);

    private final ServiceDiscovery serviceDiscovery;

    private final CommonSerializer serializer;

    public SocketClient() {
        //以默认序列化器调用构造函数
        this(DEFAULT_SERIALIZER, new RandomLoadBalancer());
    }

    public SocketClient(LoadBalancer loadBalancer){
        this(DEFAULT_SERIALIZER, loadBalancer);
    }

    public SocketClient(Integer serializerCode){
        this(serializerCode, new RandomLoadBalancer());
    }

    public SocketClient(Integer serializerCode, LoadBalancer loadBalancer) {
        serviceDiscovery = new NacosServiceDiscovery(loadBalancer);
        serializer = CommonSerializer.getByCode(serializerCode);
    }

    @Override
    public Object sendRequest(RpcRequest rpcRequest){
        if (serializer == null) {
            logger.error("未设置序列化器");
            throw new RpcException(RpcError.SERIALIZER_NOT_FOUND);
        }
        //从Nacos获取提供对应服务的服务端地址
        InetSocketAddress inetSocketAddress = serviceDiscovery.lookupService(rpcRequest.getInterfaceName());
        /**
         * socket套接字实现TCP网络传输
         * try()中一般放对资源的申请，若{}出现异常，()资源会自动关闭
         */
        try (Socket socket = new Socket()) {
            socket.connect(inetSocketAddress);
            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();
            ObjectWriter.writeObject(outputStream, rpcRequest, serializer);
            Object obj = ObjectReader.readObject(inputStream);
            RpcResponse rpcResponse = (RpcResponse) obj;
            RpcMessageChecker.check(rpcRequest, rpcResponse);
            return rpcResponse;
        } catch (IOException e) {
            logger.error("调用时有错误发生：" + e);
            throw new RpcException("服务调用失败：", e);
        }
    }

}
