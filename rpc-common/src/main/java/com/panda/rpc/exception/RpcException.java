package com.panda.rpc.exception;

import com.panda.rpc.enumeration.RpcError;

/**
 * @author [PANDA] 1843047930@qq.com
 * @date [2021-02-07 18:51]
 * @description Rpc调用异常
 */
public class RpcException extends RuntimeException{

    public RpcException(RpcError error, String detail){
        super(error.getMessage() + ":" + detail);
    }

    public RpcException(String message, Throwable cause){
        super(message, cause);
    }

    public RpcException(RpcError error){
        super(error.getMessage());
    }
}
