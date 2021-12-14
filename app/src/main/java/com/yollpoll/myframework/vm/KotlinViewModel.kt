package com.yollpoll.myframework.vm

import android.app.Application
import androidx.arch.core.executor.ArchTaskExecutor
import androidx.lifecycle.LiveData
import androidx.paging.*
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yollpoll.framework.fast.FastViewModel
import com.yollpoll.myframework.repository.PageListRepository

/**
 * Created by spq on 2021/4/27
 */
class KotlinViewModel(application: Application) : FastViewModel(application) {
    //生成ld
    val list = PageListRepository().list
}


//@Entity
//data class DataItem(
//        @PrimaryKey(autoGenerate = true)
//        var id: Int,
//        val name: String,
//        val content: String)