package com.panda.rpc.factory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author [PANDA] 1843047930@qq.com
 * @date [2021-03-14 11:14]
 * @description 单例工厂，保证一个类只有一个实例，节约资源，保证线程安全
 */
public class SingletonFactory {

    private static Map<Class, Object> objectMap = new HashMap<>();

    private SingletonFactory(){}

    public static <T> T getInstance(Class<T> clazz) {
        Object instance = objectMap.get(clazz);
        //锁住类保证线程安全
        synchronized (clazz){
            if(instance == null){
                try {
                    instance = clazz.newInstance();
                    objectMap.put(clazz, instance);
                }catch (IllegalAccessException | InstantiationException e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
        }
        return clazz.cast(instance);
    }
}
