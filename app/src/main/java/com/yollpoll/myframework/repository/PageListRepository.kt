package com.yollpoll.myframework.repository

import androidx.paging.Config
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.yollpoll.framework.widgets.list.PageSourceFactor
import com.yollpoll.myframework.room.MainDB
import com.yollpoll.myframework.ui.pagingdemo.DataItem

/**
 * Created by spq on 2021/4/30
 */
class PageListRepository {
    val myPagingConfig = Config(
            pageSize = 50,//页面数量
            initialLoadSizeHint = 150,//初次加载数量
            prefetchDistance = 50,//预加载时候给定界面最后一个可见项
            enablePlaceholders = true
    )

    //factory
    val dataSourceFactory = PageSourceFactor<DataItem>()

    //    //生成ld
//    val list =
//            dataSourceFactory.toLiveData(myPagingConfig, boundaryCallback = MyBoundaryCallback())
//生成ld
    val list =
            MainDB.getDBInstace().getDataItemDao().query().toLiveData(myPagingConfig, boundaryCallback = MyBoundaryCallback())


    fun invalidateDataSource() =
            dataSourceFactory.sourceLiveData.value?.invalidate()

    fun refresh() {
        val array = arrayListOf<DataItem>()
        for (i in 0..200) {
            val data = DataItem(i, "name:" + i, "contet:" + i)
            array.add(data)
        }
//        MainDB.getDBInstace().getDataItemDao().insertAll(array)
    }

    /**
     * 这个是用来处理加载时机的
     */
    class MyBoundaryCallback : PagedList.BoundaryCallback<DataItem>() {
        override fun onItemAtEndLoaded(itemAtEnd: DataItem) {
            super.onItemAtEndLoaded(itemAtEnd)
            //最后一条数据加载完成，接下来可以请求服务器

        }
    }
}
