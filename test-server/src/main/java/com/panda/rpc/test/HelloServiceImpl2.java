package com.panda.rpc.test;

import com.panda.rpc.api.HelloObject;
import com.panda.rpc.api.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author [PANDA] 1843047930@qq.com
 * @date [2021-03-14 10:32]
 * @description 服务端api接口实现
 */
public class HelloServiceImpl2 implements HelloService {

    private static final Logger logger = LoggerFactory.getLogger(HelloServiceImpl2.class);

    @Override
    public String hello(HelloObject object) {
        logger.info("接收到消息：{}", object.getMessage());
        return "本次处理来自Socket服务";

    }
}
