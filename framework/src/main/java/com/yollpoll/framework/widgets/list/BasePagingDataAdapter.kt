package com.yollpoll.framework.widgets.list

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil


/**
 * paging3.0
 * @param T : Any t必须重写equal方法，建议使用data关键字生成
 * @property layoutId Int 布局
 * @property variableId Int? dataBinding id
 * @property placeHolder Boolean 是否启用占位
 * @property itemSame Function2<T, T, Boolean> itemType是否相同
 * @property context Context
 * @constructor 构造方法
 */
class BasePagingDataAdapter<T : Any>(
    private val layoutId: Int,
    private val variableId: Int?,
    private val placeHolder: Boolean = false,
    val itemSame: (T, T) -> Boolean
) : PagingDataAdapter<T, BaseViewHolder<T>>(object : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return itemSame.invoke(oldItem, newItem)
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }
}) {

    lateinit var context: Context

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        val item = getItem(position)
        if (null != item) {
            holder.bind(item)
        } else if (placeHolder) {
            holder.clear()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        context = parent.context
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(context),
            layoutId,
            parent,
            false
        )
        return BaseViewHolder(binding, variableId)
    }

}