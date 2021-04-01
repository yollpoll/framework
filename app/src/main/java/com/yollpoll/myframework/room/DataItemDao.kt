package com.yollpoll.myframework.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.room.Dao
import androidx.room.Query
import com.yollpoll.myframework.ui.pagingdemo.DataItem

/**
 * Created by spq on 2021/1/20
 */

@Dao
interface DataItemDao :BaseDao<DataItem>{
    @Query("SELECT * FROM DATAITEM")
    fun query():DataSource.Factory<Int,DataItem>

}