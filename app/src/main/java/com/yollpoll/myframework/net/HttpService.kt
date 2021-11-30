package com.yollpoll.myframework.net

import com.example.nmbcompose.bean.ForumList
import retrofit2.http.GET

/**
 * Created by spq on 2021/9/1
 */
interface HttpService {

    @GET(FORUM_LIST)
    suspend fun getForum(): ForumList
}