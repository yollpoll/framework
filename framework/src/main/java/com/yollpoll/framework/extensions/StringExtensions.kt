package com.yollpoll.framework.extensions

import com.yollpoll.arch.util.ToastUtil


/**
 * Created by spq on 2021/7/27
 */
fun String.shortToast() {
    ToastUtil.showShortToast(this)
}

fun String.longToast() {
    ToastUtil.showLongToast(this)
}
