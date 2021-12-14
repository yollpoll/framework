package com.yollpoll.framework.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import java.io.Serializable

/**
 * Created by spq on 2021/5/13
 * activity、fragment拓展函数
 */
inline fun <reified T : Activity> Activity.startActivity() {
    startActivity(Intent(this, T::class.java))
}

inline fun <reified T : Activity> Activity.startActivityForResult(reqCode: Int) {
    startActivityForResult(Intent(this, T::class.java), reqCode)
}

inline fun <reified T : Activity> Activity.startActivity(vararg args: Pair<String, Serializable>) {
    val intent = Intent(this, T::class.java)
    args.let {
        for (arg in args) {
            intent.putExtra(arg.first, arg.second)
        }
    }
    startActivity(intent)
}

inline fun <reified T : Activity> Activity.startActivityForResult(
    reqCode: Int,
    vararg args: Pair<String, Serializable>
) {
    val intent = Intent(this, T::class.java)
    args.let {
        for (arg in args) {
            intent.putExtra(arg.first, arg.second)
        }
    }
    startActivityForResult(intent, reqCode)
}

inline fun <reified F : Fragment> AppCompatActivity.newFragment(vararg args: Pair<String, String>): F {
    val bundle = Bundle()
    //解包
    args.let {
        for (arg in args) {
            bundle.putString(arg.first, arg.second)
        }
    }
    return this.supportFragmentManager.fragmentFactory.instantiate(
        this.classLoader,
        F::class.java.name
    ) as F
}