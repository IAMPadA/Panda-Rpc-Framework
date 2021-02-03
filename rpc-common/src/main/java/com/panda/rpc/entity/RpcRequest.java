package com.panda.rpc.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author [PANDA] 1843047930@qq.com
 * @date [2021-02-03 16:16]
 * @description 传输格式（传输协议）：客户端向服务端传输的对象
 */
@Data
//使用创建者模式，一次性给所有变量初始赋值
@Builder
public class RpcRequest implements Serializable {
    /**
     * 待调用接口名称
     */
    private String interfaceName;
    /**
     * 待调用方法名称
     */
    private String methodName;
    /**
     * 待调用方法的参数
     */
    private Object[] parameters;
    /**
     * 待调用方法的参数类型
     */
    private Class<?>[] paramTypes;
}
