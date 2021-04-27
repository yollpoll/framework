package com.yollpoll.myframework

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.yollpoll.fast.FastActivity
import com.yollpoll.framework.annotation.ContentView
import com.yollpoll.framework.annotation.ViewModel
import com.yollpoll.framework.extend.getInt
import com.yollpoll.framework.extend.putInt
import com.yollpoll.framework.utils.ToastUtil
import com.yollpoll.myframework.databinding.ActivityKotlinBinding
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                context.putInt("key", 1345)
            }
           val res:Int= context.getInt("key",54321)
            ToastUtil.showLongToast(res.toString())
        }
    }
}