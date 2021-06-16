package com.yollpoll.myframework.paging

import android.app.Application
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import com.yollpoll.fast.FastViewModel

/**
 * Created by spq on 2021/5/11
 */
class Paging3ViewModel(application: Application) : FastViewModel(application) {
    val flow = Pager(PagingConfig(20)) {
        MyPagingSource()
    }.flow.cachedIn(viewModelScope)
}