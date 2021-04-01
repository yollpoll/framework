package com.yollpoll.framework.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

/**
 * Created by spq on 2021/2/23
 */

fun <T> fromJson(json: String, clazz: Class<T>): T {
    return Gson().fromJson(json, clazz)
}

fun <T> fromJson(json: String, typeOfT: Type): T {
    return Gson().fromJson(json, typeOfT)
}

fun <T> fromJson(json: String, typeToken: TypeToken<T>): T {
    return Gson().fromJson(json, typeToken.type);
}

fun toJson(any: Any): String {
    return Gson().toJson(any)
}