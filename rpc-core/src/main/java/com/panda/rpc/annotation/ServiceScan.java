package com.panda.rpc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author [PANDA] 1843047930@qq.com
 * @date [2021-03-16 10:07]
 * @description 标识服务的扫描的包的范围,即扫描范围的根包
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ServiceScan {

    public String value() default "";

}
