package com.yollpoll.myframework.room

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.yollpoll.arch.base.BaseApplication
import com.yollpoll.myframework.paging.PagingItem
import com.yollpoll.myframework.ui.pagingdemo.DataItem
import com.yollpoll.myframework.ui.pagingdemo.PagingVM

/**
 * Created by spq on 2021/1/20
 */
@Database(entities = [DataItem::class, PagingItem::class], version = 1)
abstract class MainDB : RoomDatabase() {
    abstract fun getDataItemDao(): DataItemDao

    //    abstract fun getPaging3Dao(): Paging3Dao
    abstract fun paging3Dao(): Paging3Dao

    companion object {
        @Volatile
        private var instance: MainDB? = null

        fun getDBInstace(): MainDB {

            if (instance == null) {

                synchronized(MainDB::class) {

                    if (instance == null) {

                        instance = Room.databaseBuilder(
                                BaseApplication.getINSTANCE(),
                                MainDB::class.java,
                                "yollpoll.db"
                        )
                                .allowMainThreadQueries()
                                .build()
                    }
                }
            }
            return instance!!

        }
    }
}