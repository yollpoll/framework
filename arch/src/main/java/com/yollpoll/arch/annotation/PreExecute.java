package com.yollpoll.arch.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by spq on 2021/2/28
 * 预处理注解，使用方法:
 * 在viewModel的command属性上加入PreExecute注解（方法名作为参数）
 * 然后在activity 声明一个boolean 类型的返回值的方法做预处理
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface PreExecute {
    /**
     * @return view中需要处理的方法
     */
    String value();
}
