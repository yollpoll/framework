package com.yollpoll.myframework.room

import androidx.room.*

/**
 * Created by spq on 2021/1/20
 */

@Dao
interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: MutableList<T>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: Array<T>)
    @Delete
    fun delete(element: T)

    @Delete
    fun deleteList(elements: MutableList<T>)

    @Delete
    fun deleteSome(vararg elements: T)

    @Update
    fun update(element: T)
}