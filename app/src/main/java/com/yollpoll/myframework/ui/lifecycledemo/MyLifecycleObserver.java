package com.yollpoll.myframework.ui.lifecycledemo;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * Created by spq on 2020-06-05
 */
public class MyLifecycleObserver  implements LifecycleObserver {
    private static final String TAG = "MyLifecycleObserver";
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onConnectListener(LifecycleOwner owner){
        if(null!=owner){
            Log.d(TAG, "onConnectListener: owner is not null");
        }
        Log.d(TAG, "onConnectListener: onLifecycle resume");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onstart(){
        Log.d(TAG, "onstart: onLifecycle start");
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onDisconnectListener(){
        Log.d(TAG, "onDisconnectListener: onLifecycle pause");
    }
}
