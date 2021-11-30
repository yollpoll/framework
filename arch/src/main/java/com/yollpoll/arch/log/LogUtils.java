package com.yollpoll.arch.log;

import android.content.Context;
import android.content.pm.ApplicationInfo;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;


/**
 * Created by spq on 2020-05-20
 */
//todo appId需要一个appCenter去根据不同项目获取，这边暂时写死
public class LogUtils {
    public static void init(Context context, String tag, final boolean isLoggable) {
        if (tag == null || tag.equals("")) {
            tag = "yollpoll";
        }
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .tag(tag)   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();
        final boolean isDebug = ((context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0);
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return isDebug && isLoggable;
            }
        });
    }

    public static void d(String message, Object... args) {
        Logger.d(message, args);
    }

    public static void d(Object object) {
        Logger.d(object);
    }

    public static void e(String message, Object... args) {
        Logger.e(null, message, args);
    }

    public static void e(Throwable throwable, String message, Object... args) {
        Logger.e(throwable, message, args);
    }

    public static void i(String message, Object... args) {
        Logger.i(message, args);
    }

    public static void v(String message, Object... args) {
        Logger.v(message, args);
    }

    public static void w(String message, Object... args) {
        Logger.w(message, args);
    }

    public static void wtf(String message, Object... args) {
        Logger.wtf(message, args);
    }

    /**
     * Formats the json content and print it
     *
     * @param json the json content
     */
    public static void json(String json) {
        Logger.json(json);
    }

    /**
     * Formats the json content and print it
     *
     * @param xml the xml content
     */
    public static void xml(String xml) {
        Logger.xml(xml);
    }
}
