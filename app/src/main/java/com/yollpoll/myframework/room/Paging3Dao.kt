package com.yollpoll.myframework.room

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yollpoll.myframework.paging.PagingItem

/**
 * Created by spq on 2021/5/11
 */
@Dao
interface Paging3Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<PagingItem>)

    @Query("SELECT * FROM PAGINGITEM WHERE name LIKE :query")
    fun pagingSource(query: String): PagingSource<Int, PagingItem>


    @Query("DELETE FROM PAGINGITEM")
    suspend fun clearAll()
}