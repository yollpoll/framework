package com.yollpoll.myframework.ui.fragment

import android.os.Bundle
import android.view.View
import com.yollpoll.annotation.annotation.OnMessage
import com.yollpoll.arch.annotation.ContentView
import com.yollpoll.arch.annotation.ViewModel
import com.yollpoll.framework.fast.FastFragment
import com.yollpoll.framework.utils.ToastUtil
import com.yollpoll.myframework.R
import com.yollpoll.myframework.databinding.FragmentTest2Binding

/**
 * Created by spq on 2021/6/24
 */
@ContentView(R.layout.fragment_test_2)
@ViewModel(TestFragmentViewModel::class)
class TestFragment : FastFragment<FragmentTest2Binding, TestFragmentViewModel>() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showToast()
    }
    @OnMessage
    fun showToast(){
        ToastUtil.showShortToast("加载完成")
    }
}