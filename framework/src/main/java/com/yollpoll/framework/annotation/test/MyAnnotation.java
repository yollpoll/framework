package com.yollpoll.framework.annotation.test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by spq on 2021/2/12
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotation {
    String[] value() default "default";

    int version() default 1;
}
