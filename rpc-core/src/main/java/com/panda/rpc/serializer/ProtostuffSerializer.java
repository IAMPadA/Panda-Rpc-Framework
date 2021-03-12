package com.panda.rpc.serializer;

import com.panda.rpc.enumeration.SerializerCode;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author [PANDA] 1843047930@qq.com
 * @date [2021-03-11 18:42]
 * @description Protostuff序列化器
 */
public class ProtostuffSerializer implements CommonSerializer {

    /**
     * 避免每次序列化都重新申请Buffer空间,用来存放对象序列化之后的数据
     * 如果你设置的空间不足，会自动扩展的，但这个大小还是要设置一个合适的值，设置大了浪费空间，设置小了会自动扩展浪费时间
     */
    private LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);

    /**
     * 缓存类对应的Schema，由于构造schema需要获得对象的类和字段信息，会用到反射机制
     *这是一个很耗时的过程，因此进行缓存很有必要，下次遇到相同的类直接从缓存中get就行了
     */
    private Map<Class<?>, Schema<?>> schemaCache = new ConcurrentHashMap<>();

    @Override
    @SuppressWarnings("unchecked")
    public byte[] serialize(Object obj) {
        Class clazz = obj.getClass();
        Schema schema = getSchema(clazz);
        byte[] data;
        try {
            //序列化操作，将对象转换为字节数组
            data = ProtostuffIOUtil.toByteArray(obj, schema, buffer);
        } finally {
            //使用完清空buffer
            buffer.clear();
        }
        return data;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object deserialize(byte[] bytes, Class<?> clazz) {
        Schema schema = getSchema(clazz);
        Object obj = schema.newMessage();
        //反序列化操作，将字节数组转换为对应的对象
        ProtostuffIOUtil.mergeFrom(bytes, obj, schema);
        return obj;
    }

    @Override
    public int getCode() {
        return SerializerCode.valueOf("PROTOBUF").getCode();
    }

    /**
     * @description 获取Schema
     * @param [clazz]
     * @return [io.protostuff.Schema]
     * @date [2021-03-11 21:38]
     */
    @SuppressWarnings("unchecked")
    private Schema getSchema(Class clazz) {
        //首先尝试从Map缓存中获取类对应的schema
        Schema schema = schemaCache.get(clazz);
        if(Objects.isNull(schema)) {
            //新创建一个schema，RuntimeSchema就是将schema繁琐的创建过程封装了起来
            //它的创建过程是线程安全的,采用懒创建的方式，即当需要schema的时候才创建
            schema = RuntimeSchema.getSchema(clazz);
            if(Objects.nonNull(schema)) {
                //缓存schema，方便下次直接使用
                schemaCache.put(clazz, schema);
            }
        }
        return schema;
    }
}