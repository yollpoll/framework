package com.yollpoll.framework.net.http

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by spq on 2021/4/20
 */
class RetrofitFactory(intercept: RetrofitIntercept) {
    private val okHttpClient: OkHttpClient
    private val builder: OkHttpClient.Builder = OkHttpClient.Builder()
    private val retrofit: Retrofit
    private val retrofitBuilder: Retrofit.Builder = Retrofit.Builder()

    init {
        intercept.okHttpClientBuilder(builder)
        intercept.retrofitBuilder(retrofitBuilder)

        okHttpClient = builder.build()
        retrofit = retrofitBuilder
            .baseUrl(intercept.baseUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
        intercept.retrofit(retrofit)
        intercept.okHttpClient(okHttpClient)
    }

    fun <T> createService(clz: Class<T>) = retrofit.create(clz)

}

interface RetrofitIntercept {
    fun baseUrl(): String
    fun okHttpClientBuilder(builder: OkHttpClient.Builder)
    fun retrofitBuilder(builder: Retrofit.Builder)
    fun retrofit(retrofit: Retrofit)
    fun okHttpClient(client: OkHttpClient)
}