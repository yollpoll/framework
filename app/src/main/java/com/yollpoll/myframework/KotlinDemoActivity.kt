package com.yollpoll.myframework

import android.os.Bundle
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.yollpoll.fast.FastActivity
import com.yollpoll.framework.annotation.ContentView
import com.yollpoll.framework.annotation.ViewModel
import com.yollpoll.framework.extend.getInt
import com.yollpoll.framework.extend.putInt
import com.yollpoll.framework.utils.ToastUtil
import com.yollpoll.framework.widgets.list.BasePageListAdapter
import com.yollpoll.framework.widgets.list.BaseViewHolder
import com.yollpoll.framework.widgets.list.MyPageListAdapter
import com.yollpoll.myframework.databinding.ActivityKotlinBinding
import com.yollpoll.myframework.ui.pagingdemo.DataItem
import com.yollpoll.myframework.vm.KotlinViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by spq on 2021/4/27
 */
@ContentView(R.layout.activity_kotlin)
@ViewModel(KotlinViewModel::class)
class KotlinDemoActivity : FastActivity<ActivityKotlinBinding, KotlinViewModel>() {
    val mAdapter = MyPageListAdapter<DataItem>(
            R.layout.item_paging,
            BR.bean,
            false,
            object : DiffUtil.ItemCallback<DataItem>() {
                override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                    return oldItem == newItem
                }

                override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                    return oldItem.id == newItem.id
                }
            })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDataBinding.rvPaging.adapter = mAdapter
        mDataBinding.rvPaging.layoutManager = LinearLayoutManager(this)
        mViewModel.list.observe(this, Observer {list->
            mAdapter.submitList(list)
        })
    }

}