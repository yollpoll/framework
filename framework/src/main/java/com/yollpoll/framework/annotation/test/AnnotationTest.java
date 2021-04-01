package com.yollpoll.framework.annotation.test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by spq on 2021/2/12
 */
public class AnnotationTest {
    public static void main(String[] args) throws Exception {
        Person person = new Person();
        Class<Person> clz = Person.class;
        Method mSomeBody = clz.getMethod("somebody", new Class[]{String.class, int.class});
        mSomeBody.invoke(person, "spq", 30);
        iteratorAnnotations(mSomeBody);

        Method mEmpty=clz.getMethod("empty",new Class[]{});
        mEmpty.invoke(person,new Object[]{});
        iteratorAnnotations(mEmpty);

    }

    public static void iteratorAnnotations(Method method) {
        //判断方法是否有该注解
        if (method.isAnnotationPresent(MyAnnotation.class)) {
            MyAnnotation annotation = method.getAnnotation(MyAnnotation.class);
            String values[] = annotation.value();
            int version=annotation.version();
            for (String str:values){
                System.out.println(" value:"+str+", version:"+version+" ,");
            }
        }
        Annotation[] annotations=method.getAnnotations();
        for (Annotation annotation:annotations){
            System.out.println(annotation);
        }
    }
}
