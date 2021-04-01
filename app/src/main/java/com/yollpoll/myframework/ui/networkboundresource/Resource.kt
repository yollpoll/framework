package com.yollpoll.myframework.ui.networkboundresource

/**
 * Created by spq on 2021/1/25
 */
sealed class Resource<T>(
        val date: T? = null,
        val message: String? = null
) {
    class Success<T>(date: T) : Resource<T>(date)
    class Loading<T>(date: T? = null) : Resource<T>(date)
    class Error<T>(message: String, date: T? = null) : Resource<T>(date, message)
}