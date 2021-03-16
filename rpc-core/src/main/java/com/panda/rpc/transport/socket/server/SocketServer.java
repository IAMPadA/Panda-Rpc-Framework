package com.panda.rpc.transport.socket.server;

import com.panda.rpc.hook.ShutdownHook;
import com.panda.rpc.provider.ServiceProvider;
import com.panda.rpc.provider.ServiceProviderImpl;
import com.panda.rpc.register.NacosServiceRegistry;
import com.panda.rpc.transport.AbstractRpcServer;
import com.panda.rpc.transport.RpcServer;
import com.panda.rpc.enumeration.RpcError;
import com.panda.rpc.exception.RpcException;
import com.panda.rpc.register.ServiceRegistry;
import com.panda.rpc.handler.RequestHandler;
import com.panda.rpc.serializer.CommonSerializer;
import com.panda.rpc.factory.ThreadPoolFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * @author [PANDA] 1843047930@qq.com
 * @date [2021-02-05 11:34]
 * @description Socket方式进行远程调用连接的服务端
 */
public class SocketServer extends AbstractRpcServer {

    private final ExecutorService threadPool;
    private final CommonSerializer serializer;
    private final RequestHandler requestHandler = new RequestHandler();

    public SocketServer(String host, int port) {
        this(host, port, DEFAULT_SERIALIZER);
    }

    public SocketServer(String host, int port, Integer serializerCode){
        this.host = host;
        this.port = port;
        serviceRegistry = new NacosServiceRegistry();
        serviceProvider = new ServiceProviderImpl();
        serializer = CommonSerializer.getByCode(serializerCode);
        //创建线程池
        threadPool = ThreadPoolFactory.createDefaultThreadPool("socket-rpc-server");
        //自动注册服务
        scanServices();
    }

    /**
     * @description 服务端启动
     * @param []
     * @return [void]
     * @date [2021-02-05 11:57]
     */
    @Override
    public void start(){
        try(ServerSocket serverSocket = new ServerSocket()){
            serverSocket.bind(new InetSocketAddress(host, port));
            logger.info("服务器启动……");
            //添加钩子，服务端关闭时会注销服务
            ShutdownHook.getShutdownHook().addClearAllHook();
            Socket socket;
            //当未接收到连接请求时，accept()会一直阻塞
            while ((socket = serverSocket.accept()) != null){
                logger.info("客户端连接！{}:{}", socket.getInetAddress(), socket.getPort());
                threadPool.execute(new SocketRequestHandlerThread(socket, requestHandler, serializer));
            }
            threadPool.shutdown();
        }catch (IOException e){
            logger.info("服务器启动时有错误发生：" + e);
        }
    }

}
