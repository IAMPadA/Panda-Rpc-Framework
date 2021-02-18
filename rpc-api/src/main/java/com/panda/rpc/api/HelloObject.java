package com.panda.rpc.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author [PANDA] 1843047930@qq.com
 * @date [2021-02-03 14:15]
 * @description 定义客户端传给服务端的接口的参数
 */

//自动加上所有属性的get set toString hashCode equals方法
@Data
@NoArgsConstructor
//添加一个含有所有已声明字段属性参数的构造函数
@AllArgsConstructor

public class HelloObject implements Serializable {
    //Serializable序列化接口没有任何方法或者字段，只是用于标识可序列化的语义。
    //实现了Serializable接口的类可以被ObjectOutputStream转换为字节流。
    //同时也可以通过ObjectInputStream再将其解析为对象。
    //序列化是指把对象转换为字节序列的过程;反序列化则是把持久化的字节文件数据恢复为对象的过程。

    private Integer id;
    private String message;

}
