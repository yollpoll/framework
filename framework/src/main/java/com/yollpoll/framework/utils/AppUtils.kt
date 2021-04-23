package com.yollpoll.framework.utils

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import com.yollpoll.annotation.annotation.OnMessage
import com.yollpoll.framework.log.LogUtils
import java.lang.reflect.InvocationTargetException

/**
 * Created by spq on 2021/3/4
 *
 */
private const val TAG = "AppUtils"

object AppUtils{
    @SuppressLint("StaticFieldLeak")
    private var sApplication: Application? = null

    @JvmStatic
    fun init(context: Context) {
        init(context.applicationContext as Application)
    }

    @JvmStatic
    fun init(app: Application) {
        if (sApplication == null) {
            sApplication = app
            // Utils.sApplication.registerActivityLifecycleCallbacks(ACTIVITY_LIFECYCLE);
        }
    }

    @JvmStatic
    fun getApp(): Application? {
        if (sApplication != null) return sApplication
        try {
            @SuppressLint("PrivateApi")
            val activityThread = Class.forName("android.app.ActivityThread")
            val at = activityThread.getMethod("currentActivityThread").invoke(null)
            val app = activityThread.getMethod("getApplication").invoke(at)
                    ?: throw NullPointerException("u should init first")
            init(app as Application)
            return sApplication
        } catch (e: NoSuchMethodException) {
            LogUtils.e(TAG,TAG+".getApp 异常 ",e)
        } catch (e: IllegalAccessException) {
            LogUtils.e(TAG,TAG+".getApp 异常 ",e)
        } catch (e: InvocationTargetException) {
            LogUtils.e(TAG,TAG+".getApp 异常 ",e)
        } catch (e: ClassNotFoundException) {
            LogUtils.e(TAG,TAG+".getApp 异常 ",e)
        }

        throw NullPointerException("u should init first")
    }



    /**
     * 获取版本号
     *
     * @param context Android Context
     * @return 版本号
     */
    @JvmStatic
    fun getVersionCode(context: Context): Int {
        val pManager = context.packageManager
        var packageInfo: PackageInfo? = null
        try {
            packageInfo = pManager.getPackageInfo(context.packageName, 0)

        } catch (e: PackageManager.NameNotFoundException) {
            LogUtils.e("AppUtil.getVersionCode 异常 ",e)
        }

        return packageInfo!!.versionCode
    }

    /**
     * 获取版本名称
     *
     * @param context Android Context
     * @return 版本名称
     */
    @JvmStatic
    fun getVersionName(context: Context): String {
        val pManager = context.packageManager
        var packageInfo: PackageInfo? = null
        try {
            packageInfo = pManager.getPackageInfo(context.packageName, 0)

        } catch (e: PackageManager.NameNotFoundException) {
            LogUtils.e(TAG,TAG+".getVersionName 异常 ",e)
        }

        return packageInfo!!.versionName
    }


    /**
     * 获取应用程序包名
     *
     * @param context Android Context
     * @return 应用程序包名
     * @since 0.4.7
     */
    @JvmStatic
    fun getPackageName(context: Context): String? {
        try {
            val packageManager = context.packageManager
            val packageInfo = packageManager.getPackageInfo(context.packageName, 0)
            return packageInfo.applicationInfo.packageName
        } catch (e: Exception) {
            LogUtils.e(TAG,TAG+".getPackageName 异常 ",e)
        }

        return null
    }

    @JvmStatic
    fun getPackageInfo(context: Context): PackageInfo? {
        try {
            val packageManager = context.packageManager
            return  packageManager.getPackageInfo(context.packageName, 0)
        } catch (e: Exception) {
            LogUtils.e(TAG,TAG+".getPackageInfo 异常 ",e)
        }
        return null;
    }


    /**
     * 获取是否为debug版本，当appid与menifest中配置的package不同时，此方法不生效
     *
     * @param context
     * @return
     */
    @Deprecated("")
    @JvmStatic
    fun isDebug(context: Context): Boolean {
        try {
            val clazz = Class.forName(context.applicationInfo.packageName + ".BuildConfig")
            val field = clazz.getField("DEBUG")
            return field.getBoolean(clazz)

        } catch (e: Exception) {
            LogUtils.e(TAG,TAG+".isDebug 异常 ",e)
            return false
        }

    }

}