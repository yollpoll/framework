package com.yollpoll.framework.widgets.list

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.ItemKeyedDataSource
import androidx.paging.PageKeyedDataSource
import androidx.paging.PositionalDataSource

/**
 * Created by spq on 2021/4/30
 * DataSource三种数据源
 * 1.ItemKeyedDataSource 从当前N的item得到N+1的item使用
 * 2.PageKeyedDataSource 通过page参数加载
 * 3.PositionalDataSource 本地数据库根据position加载数据
 */
class PageDataSource<T:Any> : PageKeyedDataSource<Int, T>() {
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, T>) {

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        TODO("Not yet implemented")
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        TODO("Not yet implemented")
    }

}

class PageSourceFactor<T:Any> : DataSource.Factory<Int, T>() {
    val sourceLiveData = MutableLiveData<PageDataSource<T>>()
    var pageDataSource: PageDataSource<T>? = null
    override fun create(): DataSource<Int, T> {
        pageDataSource = PageDataSource<T>()
        sourceLiveData.postValue(pageDataSource)
        return pageDataSource as PageDataSource<T>
    }
}