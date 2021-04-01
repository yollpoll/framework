package com.yollpoll.myframework;

import android.content.Context;

import com.yollpoll.framework.router.OnBackListener;
import com.yollpoll.framework.router.OnDispatchListener;

import java.util.HashMap;

/**
 * Created by spq on 2021/2/28
 * 组件化接受调度器接受者
 */
public enum  ModelManager implements OnDispatchListener {
    INSTANCE;
    @Override
    public void onDispatch(HashMap<String, String> params, Context context, OnBackListener onBackListener) {

    }
}
