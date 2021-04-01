package com.yollpoll.framework.annotation.handler;

import androidx.annotation.CallSuper;

import com.yollpoll.framework.base.BaseActivity;
import com.yollpoll.framework.utils.RefUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by spq on 2021/3/2
 */
public class FieldAnnotationHandler {
    private ExtraAnnotationHandler extraAnnotationHandler = new ExtraAnnotationHandler();

    /**
     * 分发处理注解
     *
     * @param annotationOwner 注解拥有者
     */
    public void dispatch(Object annotationOwner) {
        ArrayList<Field> fields = new ArrayList<>(64);
        fields = RefUtils.getDeclaredFields(annotationOwner, fields, BaseActivity.class);
        for (Field field : fields) {
            Annotation[] annotations = field.getAnnotations();
            if (annotations.length > 0) {
                for (Annotation annotation : annotations) {
                    //收集所有注解
                    collectHandleFieldAnnotation(field, annotation);
                }
            }
        }
        //处理所有注解
        handleFieldAnnotationAtFirstOfCreate(annotationOwner);
    }

    /**
     * 收集注解
     *
     * @param field
     * @param annotation
     */
    @CallSuper
    public void collectHandleFieldAnnotation(Field field, Annotation annotation) {
        extraAnnotationHandler.collectHandleFieldAnnotation(field, annotation);
    }

    /**
     * 在baseActivity onCreate的最开始的时候执行注解
     *
     * @param annotationOwner
     */
    @CallSuper
    public void handleFieldAnnotationAtFirstOfCreate(Object annotationOwner) {
        extraAnnotationHandler.handleExtraAnnotations(annotationOwner);
    }
}
