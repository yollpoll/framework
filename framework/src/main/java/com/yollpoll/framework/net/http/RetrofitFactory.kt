package com.yollpoll.framework.net.http

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by spq on 2021/4/20
 */
class RetrofitFactory(intercept: RetrofitIntercept) {
    private val retrofit: Retrofit
    private val builder: OkHttpClient.Builder = OkHttpClient.Builder()
    private val retrofitBuilder: Retrofit.Builder= Retrofit.Builder()

    init {
        intercept.okHttpClient(builder)
        intercept.retrofitBuilder(retrofitBuilder)

        retrofit = Retrofit.Builder()
            .baseUrl(intercept.baseUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .client(builder.build())
            .build()
        intercept.retrofit(retrofit)
    }

    fun <T> createService(clz: Class<T>) = retrofit.create(clz)

}

interface RetrofitIntercept {
    fun baseUrl(): String
    fun okHttpClient(builder: OkHttpClient.Builder)
    fun retrofitBuilder(builder: Retrofit.Builder)
    fun retrofit(retrofit: Retrofit)
}