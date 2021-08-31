package com.yollpoll.framework.extend

import com.yollpoll.framework.log.LogUtils
import com.yollpoll.framework.utils.AppUtils
import com.yollpoll.framework.utils.ToastUtil

/**
 * Created by spq on 2021/7/27
 */
fun String.shortToast() {
    ToastUtil.showShortToast(this)
}

fun String.longToast() {
    ToastUtil.showLongToast(this)
}
