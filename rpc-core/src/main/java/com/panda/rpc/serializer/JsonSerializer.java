package com.panda.rpc.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.panda.rpc.entity.RpcRequest;
import com.panda.rpc.enumeration.SerializerCode;
import com.panda.rpc.exception.SerializeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author [PANDA] 1843047930@qq.com
 * @date [2021-02-22 14:33]
 * @description 使用Json格式的序列化器
 */
public class JsonSerializer implements CommonSerializer{

    private static final Logger logger = LoggerFactory.getLogger(JsonSerializer.class);
    //ObjectMapper支持线程安全
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(Object obj) {
        try{
            return objectMapper.writeValueAsBytes(obj);
        }catch (JsonProcessingException e){
            logger.error("序列化时有错误发生：{}", e.getMessage());
            throw new SerializeException("序列化时有错误发生");
        }
    }

    @Override
    public Object deserialize(byte[] bytes, Class<?> clazz) {
        try{
            Object obj = objectMapper.readValue(bytes, clazz);
            if(obj instanceof RpcRequest){
                obj = handleRequest(obj);
            }
            return obj;
        }catch (IOException e){
            logger.error("反序列化时有错误发生：{}", e.getMessage());
            throw new SerializeException("反序列化时有错误发生");
        }
    }

    /**
     * @description 使用JSON反序列化Object数组，无法保证反序列化后仍然为原实例类，通常直接被反序列化成String类型，因此要特殊处理
     * @param [obj]
     * @return [java.lang.Object]
     * @date [2021-02-22 15:03]
     */
    private Object handleRequest(Object obj) throws IOException{
        RpcRequest rpcRequest = (RpcRequest) obj;
        for(int i = 0; i < rpcRequest.getParamTypes().length; i++){
            Class<?> clazz = rpcRequest.getParamTypes()[i];
            if(!clazz.isAssignableFrom(rpcRequest.getParameters()[i].getClass())){
                byte[] bytes = objectMapper.writeValueAsBytes(rpcRequest.getParameters()[i]);
                rpcRequest.getParameters()[i] = objectMapper.readValue(bytes, clazz);
            }
        }
        return rpcRequest;
    }

    @Override
    public int getCode() {
        return SerializerCode.valueOf("JSON").getCode();
    }
}
