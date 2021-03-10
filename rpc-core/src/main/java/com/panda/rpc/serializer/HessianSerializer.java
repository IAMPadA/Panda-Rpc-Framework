package com.panda.rpc.serializer;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import com.panda.rpc.enumeration.SerializerCode;
import com.panda.rpc.exception.SerializeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author [PANDA] 1843047930@qq.com
 * @date [2021-03-10 10:24]
 * @description 基于Hessian协议的序列化器
 */
public class HessianSerializer implements CommonSerializer{

    private static final Logger logger = LoggerFactory.getLogger(HessianSerializer.class);

    @Override
    public byte[] serialize(Object obj) {
        HessianOutput hessianOutput = null;
        try(ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()){
            hessianOutput = new HessianOutput(byteArrayOutputStream);
            hessianOutput.writeObject(obj);
            return byteArrayOutputStream.toByteArray();
        }catch (IOException e){
            logger.error("序列化时有错误发生" + e);
            throw new SerializeException("序列化时有错误发生");
        }finally {
            if(hessianOutput != null){
                try {
                    hessianOutput.close();
                }catch (IOException e){
                    logger.error("关闭output流时有错误发生" + e);
                }
            }
        }
    }

    @Override
    public Object deserialize(byte[] bytes, Class<?> clazz) {
        HessianInput hessianInput = null;
        try(ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes)){
            hessianInput = new HessianInput(byteArrayInputStream);
            return hessianInput.readObject();
        }catch (IOException e){
            logger.error("反序列化时有错误发生" + e);
            throw new SerializeException("反序列化时有错误发生");
        }finally {
            if(hessianInput != null) {
                hessianInput.close();
            }
        }
    }

    @Override
    public int getCode() {
        return SerializerCode.valueOf("HESSIAN").getCode();
    }
}
