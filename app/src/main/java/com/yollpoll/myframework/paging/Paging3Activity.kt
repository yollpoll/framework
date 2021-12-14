package com.yollpoll.myframework.paging

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.yollpoll.arch.annotation.ContentView
import com.yollpoll.arch.annotation.ViewModel
import com.yollpoll.framework.fast.FastActivity
import com.yollpoll.framework.paging.BasePagingDataAdapter
import com.yollpoll.myframework.BR
import com.yollpoll.myframework.R
import com.yollpoll.myframework.databinding.ActivityPaging3Binding
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

/**
 * Created by spq on 2021/5/11
 */
@ContentView(R.layout.activity_paging3)
@ViewModel(Paging3ViewModel::class)
class Paging3Activity : FastActivity<ActivityPaging3Binding, Paging3ViewModel>() {
    val adapter =
        BasePagingDataAdapter<String>(
            R.layout.item_paging3, BR.bean, onBindViewHolder = { pos, vh -> }
        )
    val manager=LinearLayoutManager(context)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initFLow()
    }

    private fun initFLow() {
        // Activities can use lifecycleScope directly, but Fragments should instead use
        // viewLifecycleOwner.lifecycleScope.
        lifecycleScope.launch {
            mViewModel.flow.collectLatest {
                //监听流
                adapter.submitData(it)
            }

            adapter.loadStateFlow.collectLatest { loadStates ->
                //loadSates加载状态，notLoading,loading,error.表示三种加载状态
                println(loadStates)
            }
        }
    }

}