package com.yollpoll.framework.annotation.handler;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.yollpoll.framework.annotation.PreExecute;
import com.yollpoll.framework.base.BaseViewModel;
import com.yollpoll.framework.log.LogUtils;
import com.yollpoll.framework.mvvm.bindingwrapper.command.BindingCommand;
import com.yollpoll.framework.mvvm.bindingwrapper.command.BindingFunction;
import com.yollpoll.framework.mvvm.view.IBaseView;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by spq on 2021/3/3
 */
public class PreExtraAnnotationHandler {
    /**
     * 处理事件命令前置处理   @PreExecute
     * 获取viewModel中属性字段的注解，获取注解中的方法名
     *
     * @param object
     */
    public static void handlePreExecute(@NonNull final IBaseView object) {
        if (object != null && (object instanceof Activity || object instanceof Fragment)) {

            BaseViewModel viewModel = object.getViewModel();
            if (viewModel == null) return;

            Field[] fields = viewModel.getClass().getDeclaredFields();
            if (fields == null || fields.length == 0) return;

            for (Field field : fields) {
                PreExecute preExecute = field.getAnnotation(PreExecute.class);
                if (preExecute == null) continue;

                handlePreExecute(object, viewModel, field, preExecute.value());
            }
        }
    }

    /**
     * @param object           fragment/activity
     * @param viewModel        viewModel
     * @param field            添加了注解的属性
     * @param invokeMethodName value中的方法名
     */
    public static void handlePreExecute(@NonNull IBaseView object, BaseViewModel viewModel, Field field, String invokeMethodName) {
        try {
            if (invokeMethodName.contains("&&")) {
                invokeMethodName = invokeMethodName.split("&&")[1];
            }

//            final Method method = RefUtil.getDeclaredMethod(object, invokeMethodName);
            final Method method = object.getClass().getMethod(invokeMethodName);
            if (method == null) return;

            method.setAccessible(true);
            field.setAccessible(true);
            //获取添加了preExecute的command属性
            Object command = field.get(viewModel);
            if (command instanceof BindingCommand) {
                ((BindingCommand) command).setPreExecute(new PreExecuteBindingFunction(method, object));
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(e.getMessage());
        }
    }

    private static class PreExecuteBindingFunction implements BindingFunction<Boolean> {

        private Method methodWeakReference;
        private WeakReference<Object> objectWeakReference;

        public PreExecuteBindingFunction(Method method, Object object) {
            this.methodWeakReference = method;
            this.objectWeakReference = new WeakReference<>(object);
        }

        @Override
        public Boolean execute() {
            try {
                Object invoke = methodWeakReference.invoke(objectWeakReference.get());
                if (invoke instanceof Boolean) {
                    return (Boolean) invoke;
                }
            } catch (Exception e) {
                LogUtils.e(e.getMessage());
                return false;
            }

            return true;
        }
    }

}
