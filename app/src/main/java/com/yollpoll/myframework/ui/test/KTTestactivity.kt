package com.yollpoll.myframework.ui.test

import android.os.Bundle
import com.yollpoll.annotation.annotation.OnMessage
import com.yollpoll.fast.FastActivity
import com.yollpoll.framework.annotation.ContentView
import com.yollpoll.framework.annotation.ViewModel
import com.yollpoll.framework.extend.newFragment
import com.yollpoll.framework.extend.startActivity
import com.yollpoll.myframework.R
import com.yollpoll.myframework.databinding.ActivityKtTestBinding
import com.yollpoll.myframework.ui.fragment.TestFragment
import com.yollpoll.myframework.ui.pagingdemo.PagingDemoActivity

/**
 * Created by spq on 2021/6/20
 */
@ContentView(R.layout.activity_kt_test)
@ViewModel(KTTestViewModel::class)
class KTTestactivity : FastActivity<ActivityKtTestBinding, KTTestViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction()
            .replace(R.id.rl_content, newFragment<TestFragment>()).commit()
    }

    @OnMessage
    fun test() {

    }
}