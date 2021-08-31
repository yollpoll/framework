package com.yollpoll.myframework.navigation

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.navigation.NavigationView
import com.yollpoll.fast.FastActivity
import com.yollpoll.fast.FastViewModel
import com.yollpoll.framework.annotation.ContentView
import com.yollpoll.framework.annotation.ViewModel
import com.yollpoll.myframework.R
import com.yollpoll.myframework.databinding.ActivityNavigationBinding

/**
 * Created by spq on 2021/7/26
 */

@ContentView(R.layout.activity_navigation)
@ViewModel(HomeViewModel::class)
class HomeActivity : FastActivity<ActivityNavigationBinding, HomeViewModel>() {
    val navController by lazy {
        val navigationFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment2) as NavHostFragment
        return@lazy navigationFragment.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
}

class HomeViewModel(application: Application) : FastViewModel(application) {

}