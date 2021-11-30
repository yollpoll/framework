package com.yollpoll.arch.util;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.yollpoll.arch.log.LogUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by spq on 2021/11/30
 */
public class AppUtils {
    private static final String TAG = "AppUtils";

    private static Application sApplication = null;

    public static void init(Context context) {
        init(context.getApplicationContext());
    }

    public static void init(Application app) {
        if (sApplication == null) {
            sApplication = app;
            // Utils.sApplication.registerActivityLifecycleCallbacks(ACTIVITY_LIFECYCLE);
        }
    }


    public static Application getApp() {
        if (sApplication != null) return sApplication;
        try {
            @SuppressLint("PrivateApi")
            Class activityThread = Class.forName("android.app.ActivityThread");
            Object at = activityThread.getMethod("currentActivityThread").invoke(null);
            Object app = activityThread.getMethod("getApplication").invoke(at);
            if (null == app) {
                throw new NullPointerException("u should init first");
            }
            init((Application) app);
            return sApplication;
        } catch (NoSuchMethodException e) {
            LogUtils.e(TAG, TAG + ".getApp 异常 ", e);
        } catch (IllegalAccessException e) {
            LogUtils.e(TAG, TAG + ".getApp 异常 ", e);
        } catch (InvocationTargetException e) {
            LogUtils.e(TAG, TAG + ".getApp 异常 ", e);
        } catch (ClassNotFoundException e) {
            LogUtils.e(TAG, TAG + ".getApp 异常 ", e);
        }

        throw new NullPointerException("u should init first");
    }


    /**
     * 获取版本号
     *
     * @param context Android Context
     * @return 版本号
     */
    public static int getVersionCode(Context context) {
        PackageManager pManager = context.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = pManager.getPackageInfo(context.getPackageName(), 0);

        } catch (PackageManager.NameNotFoundException e) {
            LogUtils.e("AppUtil.getVersionCode 异常 ", e);
        }

        return packageInfo.versionCode;
    }

    /**
     * 获取版本名称
     *
     * @param context Android Context
     * @return 版本名称
     */
    public static String getVersionName(Context context) {
        PackageManager pManager = context.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = pManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            LogUtils.e(TAG, TAG + ".getVersionName 异常 ", e);
        }

        return packageInfo.versionName;
    }


    /**
     * 获取应用程序包名
     *
     * @param context Android Context
     * @return 应用程序包名
     * @since 0.4.7
     */
    public static String getPackageName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.applicationInfo.packageName;
        } catch (Exception e) {
            LogUtils.e(TAG, TAG + ".getPackageName 异常 ", e);
        }
        return null;
    }


    public static PackageInfo getPackageInfo(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            return packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (Exception e) {
            LogUtils.e(TAG, TAG + ".getPackageInfo 异常 ", e);
        }
        return null;
    }


    /**
     * 获取是否为debug版本，当appid与menifest中配置的package不同时，此方法不生效
     *
     * @param context
     * @return
     */
    @Deprecated()
    public static boolean isDebug(Context context) {
        try {
            Class clazz = Class.forName(context.getApplicationInfo().packageName + ".BuildConfig");
            Field field = clazz.getField("DEBUG");
            return field.getBoolean(clazz);

        } catch (Exception e) {
            LogUtils.e(TAG, TAG + ".isDebug 异常 ", e);
            return false;
        }

    }

}
