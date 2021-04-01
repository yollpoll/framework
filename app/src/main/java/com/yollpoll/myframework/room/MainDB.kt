package com.yollpoll.myframework.room

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.yollpoll.framework.base.BaseApplication
import com.yollpoll.myframework.ui.pagingdemo.DataItem
import com.yollpoll.myframework.ui.pagingdemo.PagingVM

/**
 * Created by spq on 2021/1/20
 */
@Database(entities = [DataItem::class], version = 1)
abstract class MainDB : RoomDatabase() {
    abstract fun getDataItemDao(): DataItemDao

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