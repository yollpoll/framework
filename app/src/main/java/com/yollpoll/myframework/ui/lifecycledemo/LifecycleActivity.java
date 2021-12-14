package com.yollpoll.myframework.ui.lifecycledemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.yollpoll.arch.base.BaseActivity;
import com.yollpoll.myframework.R;

/**
 * lifecycleOwner
 * lifecycleOwner有一个getLifecycle的方法，activity fragment默认实现这个接口
 * Created by spq on 2020-06-05
 */
public class LifecycleActivity extends BaseActivity {
    private MyLifecycleObserver myLifecycleObserver;
    //activity默认实现owner，这里自定义重写一个
    private MyLifecycleOwner myLifecycleOwner;
    public static void gotoLiftcycleActivity(Context context){
        Intent intent=new Intent(context,LifecycleActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        myLifecycleObserver=new MyLifecycleObserver();
        myLifecycleOwner=new MyLifecycleOwner();
        myLifecycleOwner.getLifecycle().addObserver(myLifecycleObserver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        myLifecycleOwner.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        myLifecycleOwner.onStart();
    }
}
