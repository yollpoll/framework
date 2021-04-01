package com.yollpoll.myframework;

import com.yollpoll.framework.base.BaseApplication;
import com.yollpoll.framework.log.LogUtils;
import com.yollpoll.framework.router.Dispatcher;
import com.yollpoll.framework.router.OnDispatchListener;

import java.util.HashMap;

/**
 * Created by spq on 2020-05-25
 */
public class MyApplication extends BaseApplication {
    public static final String LOG_TAG = "spq";

    @Override
    public void onCreate() {
        super.onCreate();
        registerModel();
        LogUtils.init(this, LOG_TAG, true);
    }

    /**
     * 组件化模块注册
     */
    public void registerModel() {
        HashMap<String, OnDispatchListener> registerMap = new HashMap<>();
        registerMap.put("app", ModelManager.INSTANCE);
        Dispatcher.registerDispatch(registerMap);
    }
}
