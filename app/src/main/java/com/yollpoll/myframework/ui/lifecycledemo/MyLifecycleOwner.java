package com.yollpoll.myframework.ui.lifecycledemo;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;

/**
 * Created by spq on 2020-06-05
 * 自定义lifecycleOwner需要实现的接口和一个类
 */
public class MyLifecycleOwner implements LifecycleOwner {
    private LifecycleRegistry lifecycleRegistry;

    public MyLifecycleOwner() {
        lifecycleRegistry=new LifecycleRegistry(this);
        lifecycleRegistry.setCurrentState(Lifecycle.State.CREATED);
    }

    public void onStart(){
        lifecycleRegistry.setCurrentState(Lifecycle.State.STARTED);
    }
    public void onResume(){
        lifecycleRegistry.setCurrentState(Lifecycle.State.RESUMED);
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return lifecycleRegistry;
    }
}
