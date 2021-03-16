package com.panda.test;

import com.panda.rpc.api.ByeService;
import com.panda.rpc.api.HelloObject;
import com.panda.rpc.api.HelloService;
import com.panda.rpc.loadbalancer.RoundRobinLoadBalancer;
import com.panda.rpc.serializer.CommonSerializer;
import com.panda.rpc.transport.RpcClientProxy;
import com.panda.rpc.transport.socket.client.SocketClient;

/**
 * @author [PANDA] 1843047930@qq.com
 * @date [2021-02-05 14:50]
 * @description 测试用客户端
 */
public class SocketTestClient {

    public static void main(String[] args) {
        SocketClient client = new SocketClient(CommonSerializer.KRYO_SERIALIZER, new RoundRobinLoadBalancer());
        //接口与代理对象之间的中介对象
        RpcClientProxy proxy = new RpcClientProxy(client);
        //创建代理对象
        HelloService helloService = proxy.getProxy(HelloService.class);
        //接口方法的参数对象
        HelloObject object = new HelloObject(12, "This is test message");
        //由动态代理可知，代理对象调用hello()实际会执行invoke()
        String res = helloService.hello(object);
        System.out.println(res);
        ByeService byeService = proxy.getProxy(ByeService.class);
        System.out.println(byeService.bye("Netty"));
    }
}
