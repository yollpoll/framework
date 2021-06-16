package com.yollpoll.myframework.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator

/**
 * Created by spq on 2021/5/13
 */
@OptIn(ExperimentalPagingApi::class)
class MyRemoteMediator ():RemoteMediator<Int,PagingItem>(){
    override suspend fun load(loadType: LoadType, state: PagingState<Int, PagingItem>): MediatorResult {
        TODO("Not yet implemented")

    }
}