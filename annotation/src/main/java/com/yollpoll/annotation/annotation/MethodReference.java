package com.yollpoll.annotation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by spq on 2021/3/31
 * 给一个方法生成一个全局MR字段
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface MethodReference {
}
