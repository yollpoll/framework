package com.yollpoll.framework.extend

import com.squareup.moshi.Moshi

/**
 * Created by spq on 2021/4/25
 */
fun <T> String.toJsonBean(clazz: Class<T>): T? {
    return Moshi.Builder().build().adapter(clazz).fromJson(this)
}

fun Any.toJson(): String {
    return Moshi.Builder().build().adapter(this.javaClass).toJson()
}