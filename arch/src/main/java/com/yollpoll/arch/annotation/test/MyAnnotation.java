package com.yollpoll.arch.annotation.test;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by spq on 2021/2/12
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotation {
    String[] value() default "default";

    int version() default 1;
}
