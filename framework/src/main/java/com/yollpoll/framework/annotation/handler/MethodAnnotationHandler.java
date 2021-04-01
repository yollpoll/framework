package com.yollpoll.framework.annotation.handler;

import androidx.annotation.CallSuper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;

import static com.yollpoll.framework.utils.RefUtils.getDeclaredMethods;

/**
 * Created by spq on 2021/3/2
 */
public class MethodAnnotationHandler {

    private MessageAnnotationHandler messageAnnotationHandler = new MessageAnnotationHandler();
    private PermissionHandler permissionHandler = new PermissionHandler();

    /**
     * 分发处理注解
     *
     * @param annotationOwner 注解的拥有者
     */
    public void dispatch(Object annotationOwner) {

        // 3.Handle Annotation with ElementType.METHOD
        ArrayList<Method> methods = new ArrayList<>(64);
        methods = getDeclaredMethods(annotationOwner, methods, 3);

        for (Method method : methods) {
            Annotation[] annotations = method.getAnnotations();
            if (annotations == null || annotations.length == 0) {
                continue;
            }

            for (Annotation annotation : annotations) {
                collectMethodAnnotation(method, annotation);
            }
        }
        handleMethodAnnotation(annotationOwner);
    }

    /**
     * 收集Method级别注解
     *
     * @param method
     * @param annotation
     */
    @CallSuper
    public void collectMethodAnnotation(Method method, Annotation annotation) {
        messageAnnotationHandler.collectMethodAnnotation(method, annotation);
//        permissionHandler.collectMethodPermission(method, annotation);
    }

    /**
     * 处理Method级注解
     *
     * @param annotationOwner
     */
    @CallSuper
    public void handleMethodAnnotation(Object annotationOwner) {
        messageAnnotationHandler.handleMethodAnnotation(annotationOwner);
//        permissionHandler.handleMethodPermission(annotationOwner);
    }
}
