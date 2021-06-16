package com.yollpoll.myframework.paging

/**
 * Created by spq on 2021/5/13
 */
object Paging3Repository {
    @JvmStatic
    suspend fun getData(pageNo: Int, pageSize: Int): List<PagingItem> {
        val res = arrayListOf<PagingItem>()
        for (i in 0..pageSize) {
            res.add(PagingItem("$pageNo/$i", System.currentTimeMillis().toInt(), "pageNo:$pageNo,pageNo:$i"))
        }
        return res
    }
}