package com.yollpoll.myframework.navigation

import android.app.Application
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.yollpoll.arch.annotation.ContentView
import com.yollpoll.arch.annotation.ViewModel
import com.yollpoll.arch.log.LogUtils
import com.yollpoll.framework.fast.FastFragment
import com.yollpoll.framework.fast.FastViewModel
import com.yollpoll.myframework.R
import com.yollpoll.myframework.databinding.FragmentMenu1Binding
import com.yollpoll.myframework.databinding.FragmentMenu2Binding
import com.yollpoll.myframework.databinding.FragmentMenu3Binding

/**
 * Created by spq on 2021/7/26
 */
@ContentView(R.layout.fragment_menu1)
@ViewModel(Menu1ViewModel::class)
class Menu1Fragment : FastFragment<FragmentMenu1Binding, Menu1ViewModel>() {
    val activity by lazy {
        requireActivity() as HomeActivity
    }

    fun next() {
//        activity.navController.navigate(R.id.action_menu1_to_menu2)
        //使用safeargs传递参数和action
        val action = Menu1FragmentDirections.actionMenu1ToMenu2(title = "标题")
        Navigation.findNavController(mDataBinding.tvMenu).navigate(action)
    }

}

class Menu1ViewModel(application: Application) : FastViewModel(application) {
}


@ContentView(R.layout.fragment_menu2)
@ViewModel(Menu2ViewModel::class)
class Menu2ragment : FastFragment<FragmentMenu2Binding, Menu2ViewModel>() {
    var args: Menu2ragmentArgs? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        args = arguments?.let { return@let Menu2ragmentArgs.fromBundle(it) }
    }

    fun next() {
        LogUtils.i(args?.title,args)
//        args?.title?.shortToast()
//        args?.title?.logI()
    }
}

class Menu2ViewModel(application: Application) : FastViewModel(application) {

}


@ContentView(R.layout.fragment_menu3)
@ViewModel(Menu3ViewModel::class)
class Menu3Fragment : FastFragment<FragmentMenu3Binding, Menu3ViewModel>() {
    fun next() {

    }
}

class Menu3ViewModel(application: Application) : FastViewModel(application) {

}