package com.yollpoll.arch.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by spq on 2021/3/2
 * 反射工具类
 */
public class RefUtils {
    /**
     * 获取子类和父类中所有的field
     *
     * @param object
     * @param fieldArrayList
     * @param level          父类层级
     * @return
     */
    public static ArrayList<Field> getDeclaredFields(Object object, ArrayList<Field> fieldArrayList, int level) {
        Class<?> clazz = object.getClass();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            if (fieldArrayList == null || level <= 0) return fieldArrayList;
            try {
                for (Field field : clazz.getDeclaredFields()) {
                    fieldArrayList.add(field);
                }
            } catch (Exception e) {
                //e.printStackTrace();
            }
            level--;
        }
        return fieldArrayList;
    }

    /**
     * 获取到指定父类为止的field
     *
     * @param object         对象
     * @param fieldArrayList fields
     * @param targetClass    指定父类
     * @return
     */
    public static ArrayList<Field> getDeclaredFields(Object object, ArrayList<Field> fieldArrayList, Class<?> targetClass) {
        Class<?> clazz = object.getClass();
        for (; clazz != targetClass; clazz = clazz.getSuperclass()) {
            if (fieldArrayList == null || clazz == null) return fieldArrayList;
            try {
                Collections.addAll(fieldArrayList, clazz.getDeclaredFields());
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }
        return fieldArrayList;
    }

    /**
     * 获得到相应层级为止的所有method
     *
     * @param object
     * @param methodArrayList
     * @param level
     * @return
     */
    public static ArrayList<Method> getDeclaredMethods(Object object, ArrayList<Method> methodArrayList, int level) {
        Class<?> clazz = object.getClass();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            if (methodArrayList == null || level <= 0) return methodArrayList;
            try {
                Collections.addAll(methodArrayList, clazz.getDeclaredMethods());
            } catch (Exception e) {
                //e.printStackTrace();
            }
            level--;
        }
        return methodArrayList;
    }

    /**
     * 获得到相应层级为止的所有method
     *
     * @param object          目标对象
     * @param methodArrayList 目标数组
     * @param superClass      目标父类
     * @return
     */
    public static ArrayList<Method> getDeclaredMethods(Object object, ArrayList<Method> methodArrayList, Object superClass) {
        Class<?> clazz = object.getClass();
        for (; clazz != superClass; clazz = clazz.getSuperclass()) {
            if (methodArrayList == null) return methodArrayList;
            try {
                Collections.addAll(methodArrayList, clazz.getDeclaredMethods());
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }
        return methodArrayList;
    }
}
