package com.yollpoll.framework.widgets

import android.app.Dialog
import android.content.Context
import com.yollpoll.framework.R
import com.yollpoll.framework.databinding.DialogLoadingBinding
import com.yollpoll.framework.extensions.dp2px

/**
 * Created by spq on 2021/5/8
 */
class LoadingDialog(context: Context) : BaseDialog<DialogLoadingBinding, Dialog>(context) {
    companion object {
        var dialog: LoadingDialog? = null
        fun showLoading(context: Context) {
            dialog ?: let {
                dialog = LoadingDialog(context)
                dialog
            }?.show()
        }

        fun hide() {
            dialog?.dismiss()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.dialog_loading
    }

    override fun createDialog(context: Context): Dialog {
        return Dialog(context, R.style.DialogStyle)
    }

    override fun onInit(dialog: Dialog, binding: DialogLoadingBinding?) {
        val lp = dialog.window?.attributes
        lp?.height = mContext.dp2px(150F).toInt()
        lp?.width = mContext.dp2px(130F).toInt()
        dialog.window?.attributes = lp
        dialog.setCanceledOnTouchOutside(false)
    }

    override fun onDialogDismiss(dialog: Dialog?) {
    }

    override fun onDialogShow(dialog: Dialog?) {
    }
}