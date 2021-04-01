package com.yollpoll.framework.annotation.test;

import com.yollpoll.framework.annotation.test.MyAnnotation;

/**
 * Created by spq on 2021/2/12
 */
public class Person {
    @MyAnnotation
    public void empty(){
        System.out.println("empty");
    }
    @MyAnnotation(value = "2")
    public void somebody(String name,int age){
        System.out.println("someBody: "+name+" age:"+age);
    }
}
