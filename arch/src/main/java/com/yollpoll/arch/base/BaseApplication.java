package com.yollpoll.arch.base;

import android.app.Application;

import com.yollpoll.arch.util.AppUtils;


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
