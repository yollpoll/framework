package com.yollpoll.framework.extend

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.yollpoll.fast.FastViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

/**
 * Created by spq on 2021/4/27
 * DataStore对context的拓展,单独放在这个文件
 */
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "setting")

fun Context.getIntWithFLow(key: String, default: Int): Flow<Int> {
    val keyBean = intPreferencesKey(key)
    return dataStore.data
            .map { preferences ->
                // No type safety.
                preferences[keyBean] ?: default
            }
}

suspend fun Context.getInt(key: String, default: Int): Int {
    return getIntWithFLow(key, default).first()
}

suspend fun Context.putInt(key: String, value: Int) {
    val keyBean = intPreferencesKey(key)
    this.dataStore.edit { settings ->
        settings[keyBean] = value
    }
}

fun Context.getStringWithFLow(key: String, default: String): Flow<String> {
    val keyBean = stringPreferencesKey(key)
    return dataStore.data
            .map { preferences ->
                // No type safety.
                preferences[keyBean] ?: default
            }
}

suspend fun Context.getString(key: String, default: String): String {
    return getStringWithFLow(key, default).first()
}

suspend fun Context.putString(key: String, value: String) {
    val keyBean = stringPreferencesKey(key)
    this.dataStore.edit { settings ->
        settings[keyBean] = value
    }
}


fun Context.getBooleanWithFLow(key: String, default: Boolean): Flow<Boolean> {
    val keyBean = booleanPreferencesKey(key)
    return dataStore.data
            .map { preferences ->
                // No type safety.
                preferences[keyBean] ?: default
            }
}

suspend fun Context.getBoolean(key: String, default: Boolean): Boolean {
    return getBooleanWithFLow(key, default).first()
}

suspend fun Context.putBoolean(key: String, value: Boolean) {
    val keyBean = booleanPreferencesKey(key)
    this.dataStore.edit { settings ->
        settings[keyBean] = value
    }
}

fun Context.getLongWithFLow(key: String, default: Long): Flow<Long> {
    val keyBean = longPreferencesKey(key)
    return dataStore.data
            .map { preferences ->
                // No type safety.
                preferences[keyBean] ?: default
            }
}

suspend fun Context.getLong(key: String, default: Long): Long {
    return getLongWithFLow(key, default).first()
}

suspend fun Context.putLong(key: String, value: Long) {
    val keyBean = longPreferencesKey(key)
    this.dataStore.edit { settings ->
        settings[keyBean] = value
    }
}

fun Context.getFloatWithFLow(key: String, default: Float): Flow<Float> {
    val keyBean = floatPreferencesKey(key)
    return dataStore.data
            .map { preferences ->
                // No type safety.
                preferences[keyBean] ?: default
            }
}

suspend fun Context.getFloat(key: String, default: Float): Float {
    return getFloatWithFLow(key, default).first()
}

suspend fun Context.putFloat(key: String, value: Float) {
    val keyBean = floatPreferencesKey(key)
    this.dataStore.edit { settings ->
        settings[keyBean] = value
    }
}

fun Context.getDoubleWithFLow(key: String, default: Double): Flow<Double> {
    val keyBean = doublePreferencesKey(key)
    return dataStore.data
            .map { preferences ->
                // No type safety.
                preferences[keyBean] ?: default
            }
}

suspend fun Context.getDouble(key: String, default: Double): Double {
    return getDoubleWithFLow(key, default).first()
}

suspend fun Context.putDouble(key: String, value: Double) {
    val keyBean = doublePreferencesKey(key)
    this.dataStore.edit { settings ->
        settings[keyBean] = value
    }
}

suspend fun Context.saveBean(key: String, value: Any) {
    this.putString(key, value.toJson())
}

suspend fun <T> Context.getBean(key: String, clazz: Class<T>): T? {
    return this.getString(key, "").toJsonBean(clazz)
}
