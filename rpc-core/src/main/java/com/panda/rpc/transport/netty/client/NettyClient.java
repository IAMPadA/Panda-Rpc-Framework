package com.panda.rpc.transport.netty.client;

import com.panda.rpc.entity.RpcRequest;
import com.panda.rpc.entity.RpcResponse;
import com.panda.rpc.enumeration.RpcError;
import com.panda.rpc.exception.RpcException;
import com.panda.rpc.factory.SingletonFactory;
import com.panda.rpc.loadbalancer.LoadBalancer;
import com.panda.rpc.loadbalancer.RandomLoadBalancer;
import com.panda.rpc.register.NacosServiceDiscovery;
import com.panda.rpc.register.ServiceDiscovery;
import com.panda.rpc.serializer.CommonSerializer;
import com.panda.rpc.transport.RpcClient;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;

/**
 * @author [PANDA] 1843047930@qq.com
 * @date [2021-02-22 11:08]
 * @description Netty方式客户端
 */
public class NettyClient implements RpcClient {

    private static final Logger logger = LoggerFactory.getLogger(NettyClient.class);
    private static final EventLoopGroup group;
    private static final Bootstrap bootstrap;

    static {
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class);
    }

    private final ServiceDiscovery serviceDiscovery;
    private final CommonSerializer serializer;

    private final UnprocessedRequests unprocessedRequests;

    public NettyClient() {
        //以默认序列化器调用构造函数
        this(DEFAULT_SERIALIZER, new RandomLoadBalancer());
    }

    public NettyClient(LoadBalancer loadBalancer){
        this(DEFAULT_SERIALIZER, loadBalancer);
    }

    public NettyClient(Integer serializerCode){
        this(serializerCode, new RandomLoadBalancer());
    }

    public NettyClient(Integer serializerCode, LoadBalancer loadBalancer){
        serviceDiscovery = new NacosServiceDiscovery(loadBalancer);
        serializer = CommonSerializer.getByCode(serializerCode);
        unprocessedRequests = SingletonFactory.getInstance(UnprocessedRequests.class);
    }

    @Override
    public CompletableFuture<RpcResponse> sendRequest(RpcRequest rpcRequest) {
        if (serializer == null) {
            logger.error("未设置序列化器");
            throw new RpcException(RpcError.SERIALIZER_NOT_FOUND);
        }
        CompletableFuture<RpcResponse> resultFuture = new CompletableFuture<>();
        try {
            //从Nacos获取提供对应服务的服务端地址
            InetSocketAddress inetSocketAddress = serviceDiscovery.lookupService(rpcRequest.getInterfaceName());
            //创建Netty通道连接
            Channel channel = ChannelProvider.get(inetSocketAddress, serializer);
            if(!channel.isActive()) {
                group.shutdownGracefully();
                return null;
            }
            //将新请求放入未处理完的请求中
            unprocessedRequests.put(rpcRequest.getRequestId(), resultFuture);
            //向服务端发请求，并设置监听，关于writeAndFlush()的具体实现可以参考：https://blog.csdn.net/qq_34436819/article/details/103937188
            channel.writeAndFlush(rpcRequest).addListener((ChannelFutureListener) future1 -> {
                if(future1.isSuccess()){
                    logger.info(String.format("客户端发送消息：%s", rpcRequest.toString()));
                }else {
                    future1.channel().close();
                    resultFuture.completeExceptionally(future1.cause());
                    logger.error("发送消息时有错误发生: ", future1.cause());
                }
            });
        }catch (Exception e){
            //将请求从请求集合中移除
            unprocessedRequests.remove(rpcRequest.getRequestId());
            logger.error(e.getMessage(), e);
            //interrupt()这里作用是给受阻塞的当前线程发出一个中断信号，让当前线程退出阻塞状态，好继续执行然后结束
            Thread.currentThread().interrupt();
        }
        return resultFuture;
    }

}
