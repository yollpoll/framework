package com.yollpoll.framework.extend

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.yollpoll.framework.utils.MoshiArrayListJsonAdapter

/**
 * Created by spq on 2021/4/25
 */
inline fun <reified T> String.toJsonBean(): T? {
    return Moshi.Builder().add(KotlinJsonAdapterFactory()).build().adapter(T::class.java)
        .fromJson(this)
}

fun Any.toJson(): String {
    return Moshi.Builder().add(KotlinJsonAdapterFactory()).build().adapter(this.javaClass)
        .toJson(this)
}

inline fun <reified T> List<T>.toListJson(): String {
    val type = Types.newParameterizedType(List::class.java, T::class.java)
    return Moshi.Builder().add(KotlinJsonAdapterFactory()).build().adapter<List<T>>(type)
        .toJson(this)
}

inline fun <reified T> String.toListBean(): List<T>? {
    val listMyData = Types.newParameterizedType(
        MutableList::class.java,
        T::class.java
    )
    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    val adapter: JsonAdapter<List<T>> = moshi.adapter(listMyData)
    return adapter.fromJson(this)
}

inline fun <reified T, reified K> Map<T, K>.toMapJson(): String {
    val type = Types.newParameterizedType(Map::class.java, T::class.java, K::class.java)

    return Moshi.Builder().add(KotlinJsonAdapterFactory()).build().adapter<Map<T, K>>(type)
        .toJson(this)
}

inline fun <reified T, reified K> String.toMapBean(): Map<T, K>? {
    val type = Types.newParameterizedType(Map::class.java, T::class.java, K::class.java)

    return Moshi.Builder().add(KotlinJsonAdapterFactory()).build().adapter<Map<T, K>>(type)
        .fromJson(this)
}


