package com.yollpoll.framework.extensions

import android.content.Context

/**
 * Created by spq on 2021/12/6
 */
fun Context.dp2px(dp: Float): Float {
    val scale = resources.displayMetrics.density
    return (dp * scale + 0.5).toFloat()
}

fun Context.px2dp(px: Float): Float {
    val scale = resources.displayMetrics.density
    return (px / scale + 0.5f).toFloat()
}

fun Context.sp2px(sp: Float): Float {
    val fontScale = getResources().getDisplayMetrics().scaledDensity;
    return (sp * fontScale + 0.5f);
}

fun Context.px2sp(px: Float): Float {
    val fontScale = getResources().getDisplayMetrics().scaledDensity;
    return (px / fontScale + 0.5f)
}