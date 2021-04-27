package com.yollpoll.framework.extend

import android.content.Context

/**
 * Created by spq on 2021/4/22
 */

/********************************单位切换********************************/

fun Context.dp2px(dp: Float): Float {
    val scale = resources.displayMetrics.density
    return (dp * scale + 0.5).toFloat()
}

fun Context.px2dp(px: Float): Float {
    val scale = resources.displayMetrics.density
    return (px / scale + 0.5f).toFloat()
}