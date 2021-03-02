package com.panda.rpc.serializer;

/**
 * @author [PANDA] 1843047930@qq.com
 * @date [2021-02-22 14:18]
 * @description 通用的序列化反序列化接口
 */
public interface CommonSerializer {

    byte[] serialize(Object obj);

    Object deserialize(byte[] bytes, Class<?> clazz);

    int getCode();

    static CommonSerializer getByCode(int code){
        switch (code){
            case 1:
                return new JsonSerializer();
            default:
                return null;
        }
    }

}