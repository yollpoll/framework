package com.yollpoll.myframework.paging

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.yollpoll.framework.fast.FastViewModel
import com.yollpoll.framework.paging.BasePagingSource
import com.yollpoll.framework.paging.getCommonPager
import kotlinx.coroutines.flow.Flow

/**
 * Created by spq on 2021/5/11
 */
class Paging3ViewModel(application: Application) : FastViewModel(application) {
    val flow = getCommonPager {
        object : BasePagingSource<String>() {
            override suspend fun load(pos: Int): List<String> {
                return arrayListOf(
                    "item:1 page:${pos}",
                    "item:2 page:${pos}",
                    "item:3 page:${pos}",
                    "item:4 page:${pos}",
                    "item:5 page:${pos}",
                    "item:6 page:${pos}",
                    "item:7 page:${pos}",
                    "item:8 page:${pos}",
                    "item:9 page:${pos}",
                    "item:10 page:${pos}",
                    "item:11 page:${pos}",
                )
            }
        }
    }.flow.cachedIn(viewModelScope)
}