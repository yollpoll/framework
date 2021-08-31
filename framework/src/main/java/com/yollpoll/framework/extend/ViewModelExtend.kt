package com.yollpoll.framework.extend

import com.yollpoll.fast.FastViewModel
import com.yollpoll.framework.base.BaseApplication

/**
 * Created by spq on 2021/4/27
 */
/////////////////////////////////////DataStore 对vm的拓展/////////////////////////////////////

suspend fun FastViewModel.saveIntToDataStore(key: String, value: Int) {
    getApplication<BaseApplication>().putInt(key, value)
}

suspend fun FastViewModel.getIntByDataStore(key: String, default: Int): Int {
    return getApplication<BaseApplication>().getInt(key, default)
}

suspend fun FastViewModel.saveStringToDataStore(key: String, value: String) {
    getApplication<BaseApplication>().putString(key, value)
}

suspend fun FastViewModel.getStringByDataStore(key: String, default: String): String {
    return getApplication<BaseApplication>().getString(key, default)
}

suspend fun FastViewModel.saveFloatToDataStore(key: String, default: Float) {
    getApplication<BaseApplication>().putFloat(key, default)
}

suspend fun FastViewModel.getFloatByDataStore(key: String, default: Float): Float {
    return getApplication<BaseApplication>().getFloat(key, default)
}

suspend fun FastViewModel.saveLongToDataStore(key: String, value: Long) {
    getApplication<BaseApplication>().putLong(key, value)
}

suspend fun FastViewModel.getLongByDataStore(key: String, default: Long): Long {
    return getApplication<BaseApplication>().getLong(key, default)
}

suspend fun FastViewModel.saveDoubleToDataStore(key: String, value: Double) {
    getApplication<BaseApplication>().putDouble(key, value)
}

suspend fun FastViewModel.getDoubleByDataStore(key: String, default: Double): Double {
    return getApplication<BaseApplication>().getDouble(key, default)
}

suspend fun FastViewModel.saveBean(key: String, value: Any) {
    getApplication<BaseApplication>().saveBean(key, value)
}

suspend inline fun <reified T> FastViewModel.getBean(key: String): T? {
    return getStringByDataStore(key, "").toJsonBean()
}

suspend fun FastViewModel.saveList(key: String, value: List<*>) {
    getApplication<BaseApplication>().saveList(key, value)
}

suspend inline fun <reified T> FastViewModel.getList(key: String): List<T>? {
    return getStringByDataStore(key, "").toListBean()
}

suspend inline fun <reified T, reified K> FastViewModel.saveMap(key: String, value: Map<T, K>) {
    getApplication<BaseApplication>().saveMap(key, value)
}

suspend inline fun <reified T, reified K> FastViewModel.getMap(key: String): Map<T, K>? {
    return getStringByDataStore(key, "").toMapBean()
}