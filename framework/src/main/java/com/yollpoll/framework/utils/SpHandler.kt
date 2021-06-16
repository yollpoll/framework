package com.yollpoll.framework.utils

import android.content.Context
import android.content.SharedPreferences
import com.yollpoll.framework.base.BaseApplication

/**
 * Created by spq on 2021/5/6
 */

val CONFIG_FILE_NAME = AppUtils.getPackageName(BaseApplication.getINSTANCE())

fun putBoolean(key: String?, value: Boolean) {
    val sp: SharedPreferences = BaseApplication.getINSTANCE().getSharedPreferences(CONFIG_FILE_NAME, Context.MODE_PRIVATE)
    sp.edit().putBoolean(key, value).commit()
}

fun getBoolean(key: String?, defValue: Boolean): Boolean {
    val sp: SharedPreferences = BaseApplication.getINSTANCE().getSharedPreferences(CONFIG_FILE_NAME, Context.MODE_PRIVATE)
    return sp.getBoolean(key, defValue)
}

fun putString(key: String?, value: String?) {
    val sp: SharedPreferences = BaseApplication.getINSTANCE().getSharedPreferences(CONFIG_FILE_NAME, Context.MODE_PRIVATE)
    sp.edit().putString(key, value).commit()
}

fun getString(key: String?, defValue: String?): String? {
    val sp: SharedPreferences = BaseApplication.getINSTANCE().getSharedPreferences(CONFIG_FILE_NAME, Context.MODE_PRIVATE)
    return sp.getString(key, defValue)
}

fun putInt(key: String?, value: Int) {
    val sp: SharedPreferences = BaseApplication.getINSTANCE().getSharedPreferences(CONFIG_FILE_NAME, Context.MODE_PRIVATE)
    sp.edit().putInt(key, value).commit()
}

fun getInt(key: String?, defValue: Int): Int {
    val sp: SharedPreferences = BaseApplication.getINSTANCE().getSharedPreferences(CONFIG_FILE_NAME, Context.MODE_PRIVATE)
    return sp.getInt(key, defValue)
}

fun putLong(key: String?, value: Long) {
    val sp: SharedPreferences = BaseApplication.getINSTANCE().getSharedPreferences(CONFIG_FILE_NAME, Context.MODE_PRIVATE)
    sp.edit().putLong(key, value).commit()
}

fun getLong(key: String?, defValue: Long): Long {
    val sp: SharedPreferences = BaseApplication.getINSTANCE().getSharedPreferences(CONFIG_FILE_NAME, Context.MODE_PRIVATE)
    return sp.getLong(key, defValue)
}