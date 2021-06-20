package com.yollpoll.framework.widgets.list

import androidx.paging.PagingSource
import androidx.paging.PagingState
import java.lang.Exception

/**
 * Created by spq on 2021/5/11
 */
private const val START_INDEX = 0;

class MyPagingSource<T : Any> : PagingSource<Int, T>() {
    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        //在初始化加载或者加载失效以后加载这个，给下个page提供key
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    //返回一个LoadResult
    //如果请求失败，则返回LoadResult.Error
    //如果请求成功，返回loadResult.Page
    //page中需要传入数据内容的list，前一个key、后一个key
    //paging每次加载都会传递key给下一次load
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val pos = params.key ?: START_INDEX
        val startIndex = pos * params.loadSize + 1
        val endIndex = (pos + 1) * params.loadSize

        return try {
            val list = arrayListOf<T>()
            LoadResult.Page<Int, T>(
                list,
                if (pos <= START_INDEX) null else pos - 1,//上一个key
                if (list.isNullOrEmpty()) null else pos + 1
            )//下一个key
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}