package com.yollpoll.myframework.ui.pagingdemo

import android.app.Application
import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yollpoll.fast.FastViewModel
import com.yollpoll.framework.base.BaseViewModel
import com.yollpoll.myframework.room.MainDB

/**
 * Created by spq on 2021/1/19
 */
class PagingVM(application: Application) : FastViewModel(application) {
    val data: LiveData<PagedList<DataItem>>

    val config = PagedList.Config.Builder()
            .setPageSize(15)              // 分页加载的数量
            .setInitialLoadSizeHint(30)   // 初次加载的数量
            .setPrefetchDistance(10)      // 预取数据的距离
            .setEnablePlaceholders(true) // 是否启用占位符
            .build()

    init {
        //列表加载回调
        val boundary = MyBoundaryCallback()
        //room返回的是dataSource的工厂方法,这样当数据无效更新时候，会生成新的数据
        val factory = MainDB.getDBInstace().getDataItemDao().query()
        //通过LivePagedListBuilder生成对应的pagedlist
        data = LivePagedListBuilder(factory, config)
                .setBoundaryCallback(boundary)
                .build()
        initData()
    }

    fun initData() {
        val array = mutableListOf<DataItem>()
        for (i in 0..200) {
            val data = DataItem(i, "name:" + i, "contet:" + i)
            array.add(data)
        }
        MainDB.getDBInstace().getDataItemDao().insertAll(array)
    }

    /**
     * 这个是用来处理加载时机的
     */
    class MyBoundaryCallback() : PagedList.BoundaryCallback<DataItem>() {
        override fun onItemAtEndLoaded(itemAtEnd: DataItem) {
            super.onItemAtEndLoaded(itemAtEnd)
            //最后一条数据加载完成，接下来可以请求服务器
        }
    }
}


@Entity
data class DataItem(
        @PrimaryKey(autoGenerate = true)
        var id: Int,
        val name: String,
        val content: String)