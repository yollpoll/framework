package com.yollpoll.myframework.paging

import android.app.Application
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.yollpoll.fast.FastViewModel

/**
 * Created by spq on 2021/5/11
 */
class Paging3ViewModel(application: Application) : FastViewModel(application) {
    val flow = Pager(PagingConfig(20)) {
        MyPagingSource()
    }.flow.cachedIn(viewModelScope)

}