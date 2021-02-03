package com.panda.rpc.entity;

import com.panda.rpc.enumeration.ResponseCode;
import lombok.Data;

import java.io.Serializable;

/**
 * @author [PANDA] 1843047930@qq.com
 * @date [2021-02-03 17:20]
 * @description 服务端处理完后，向客户端返回的对象
 */
@Data
public class RpcResponse<T> implements Serializable {
    /**
     *响应状态码
     */
    private Integer statusCode;
    /**
     *响应状态码对应的信息
     */
    private String message;
    /**
     *成功时的响应数据
     */
    private T data;

    /**
     * @description 成功时服务端返回的对象
     * @param [data]
     * @return [com.panda.rpc.entity.RpcResponse<T>]
     * @date [2021-02-03 17:31]
     */
    public static <T> RpcResponse<T> success(T data){
        RpcResponse<T> response = new RpcResponse<>();
        response.setStatusCode(ResponseCode.SUCCESS.getCode());
        response.setData(data);
        return response;
    }

    /**
     * @description 失败时服务端返回的对象
     * @param [code]
     * @return [com.panda.rpc.entity.RpcResponse<T>]
     * @date [2021-02-03 17:42]
     */
    public static <T> RpcResponse<T> fail(ResponseCode code){
        RpcResponse<T> response = new RpcResponse<>();
        response.setStatusCode(code.getCode());
        response.setMessage(code.getMessage());
        return response;
    }
}
