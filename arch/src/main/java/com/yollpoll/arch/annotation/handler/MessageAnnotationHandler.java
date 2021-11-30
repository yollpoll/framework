package com.yollpoll.arch.annotation.handler;

import android.text.TextUtils;

import androidx.lifecycle.LifecycleOwner;


import com.yollpoll.annotation.annotation.OnMessage;
import com.yollpoll.arch.message.liveeventbus.LiveEventBus;
import com.yollpoll.arch.message.liveeventbus.NonType;
import com.yollpoll.arch.message.liveeventbus.ObserverWrapper;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by spq on 2021/3/2
 * OnMessage 注解的收集和处理
 */
public class MessageAnnotationHandler {
    Map<Method, OnMessage> mAnnotations = null;

    /**
     * 手机method注解
     */
    public void collectMethodAnnotation(Method method, Annotation annotation) {
        if (!(annotation instanceof OnMessage))
            return;
        if (null == mAnnotations) {
            mAnnotations = new HashMap<>();
        }
        mAnnotations.put(method, (OnMessage) annotation);
    }

    /**
     * 处理注解
     *
     * @param annotationOwner
     */
    public void handleMethodAnnotation(Object annotationOwner) {
        if (!(annotationOwner instanceof LifecycleOwner))
            return;
        if(null==mAnnotations)
            return;
        for (Map.Entry<Method, OnMessage> entry : mAnnotations.entrySet()) {
            OnMessage onMessage = entry.getValue();
            Method method = entry.getKey();
            Class<?>[] parms = method.getParameterTypes();

            String key = onMessage.key();
            if (TextUtils.isEmpty(key)) {
                //key为空
                key = annotationOwner.getClass().getSimpleName() + "&&" + method.getName();
            }
            if (parms.length == 1) {
                Class<?> clz = parms[0];
                LiveEventBus.use(key, clz).observe((LifecycleOwner) annotationOwner, new ObserverWrapper() {
                    @Override
                    public boolean isSticky() {
                        return onMessage.stick();
                    }

                    @Override
                    public void onChanged(Object value) {
                        try {
                            method.invoke(annotationOwner, value);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public boolean mainThread() {
                        return onMessage.mainThread();
                    }
                });
            } else {
                LiveEventBus.use(key, NonType.class).observe((LifecycleOwner) annotationOwner, new ObserverWrapper<NonType>() {
                    @Override
                    public boolean isSticky() {
                        return onMessage.stick();
                    }

                    @Override
                    public void onChanged(NonType value) {
                        try {
                            method.invoke(annotationOwner);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public boolean mainThread() {
                        return onMessage.mainThread();
                    }
                });
            }

        }
    }
}
