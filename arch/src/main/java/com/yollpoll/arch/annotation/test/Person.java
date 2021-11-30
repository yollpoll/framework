package com.yollpoll.arch.annotation.test;

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
