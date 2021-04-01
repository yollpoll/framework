package com.yollpoll.framework.base;

import android.app.Application;

import com.yollpoll.framework.log.LogUtils;
import com.yollpoll.framework.utils.AppUtils;

/**
 * Created by spq on 2020-05-25
 */
public abstract class BaseApplication extends Application {
    static BaseApplication INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();
        if (null == INSTANCE) {
            INSTANCE = this;
        }
        AppUtils.init(this);
    }

    public static BaseApplication getINSTANCE() {
        return INSTANCE;
    }
}
