package com.yollpoll.arch.annotation.handler;

import android.app.Activity;
import android.app.Dialog;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;


import com.yollpoll.arch.annotation.ContentView;
import com.yollpoll.arch.annotation.ViewModel;
import com.yollpoll.arch.base.BaseActivity;
import com.yollpoll.arch.base.BaseFragment;
import com.yollpoll.arch.base.BaseViewModel;
import com.yollpoll.arch.log.LogUtils;

import java.lang.reflect.Method;

/**
 * Created by spq on 2021/2/28
 * 注解处理
 */
public class AnnotationHandler {

    /**
     * 获取注解的layoutId
     *
     * @param object view
     * @return layoutId
     */
    public static int getLayoutId(Object object) {
        if (null == object) {
            throw new NullPointerException("layout id不能为空");
        }
        if (object instanceof BaseActivity || object instanceof BaseFragment) {
            Class<?> clz = object.getClass();
            if (clz.isAnnotationPresent(ContentView.class)) {
                return clz.getAnnotation(ContentView.class).value();
            } else {
                return 0;
            }
        }
        return 0;
    }

    /**
     * 获取view的viewModel
     *
     * @param object view
     */
    public static Class<? extends BaseViewModel> getViewModelClass(Object object) {
        if (null == object) {
            return null;
        }
        if (object instanceof BaseActivity || object instanceof BaseFragment) {
            Class<?> clz = object.getClass();
            if (clz.isAnnotationPresent(ViewModel.class)) {
                ViewModel viewModel = (ViewModel) clz.getAnnotation(ViewModel.class);
                return viewModel.value();
            } else {
                return null;
            }
        }
        return null;
    }

    /**
     * dataBinding绑定viewModel
     *
     * @param viewDataBinding
     * @param viewModel
     * @return
     */
    public static boolean setViewModelFieldValueOfViewDataBinding(@NonNull ViewDataBinding viewDataBinding, @NonNull Object viewModel) {
        if (viewModel instanceof androidx.lifecycle.ViewModel) {
            Method setViewMethod = null;
            try {
                setViewMethod = viewDataBinding.getClass().getDeclaredMethod("setViewModel", viewModel.getClass());
            } catch (NoSuchMethodException e) {
                //e.printStackTrace();
            }
            if (setViewMethod != null) {
                try {
                    setViewMethod.setAccessible(true);
                    setViewMethod.invoke(viewDataBinding, viewModel);
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.w("can't invoke method of viewDataBinding#setViewModel");
                }
            }
        }

        return false;
    }


    /**
     * dataBinding 绑定view
     *
     * @param viewDataBinding
     * @param view
     * @return
     */
    public static boolean setViewFieldValueOfViewDataBinding(@NonNull ViewDataBinding viewDataBinding, @NonNull Object view) {
        if ((view instanceof Activity || view instanceof Fragment || view instanceof Dialog)) {
            Method setViewMethod = null;
            try {
                setViewMethod = viewDataBinding.getClass().getDeclaredMethod("setView", view.getClass());
            } catch (NoSuchMethodException e) {
                try {
                    //防止与视图组件View名称冲突，改用 viewRef 变量名
                    setViewMethod = viewDataBinding.getClass().getDeclaredMethod("setViewRef", view.getClass());
                } catch (NoSuchMethodException ex) {
                }
            }
            if (setViewMethod != null) {
                try {
                    setViewMethod.setAccessible(true);
                    setViewMethod.invoke(viewDataBinding, view);
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.w("can't invoke method of viewDataBinding#setViewRef");
                }
            }
        }
        return false;
    }
}
