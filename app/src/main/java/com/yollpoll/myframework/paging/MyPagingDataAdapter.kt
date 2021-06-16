package com.yollpoll.myframework.paging

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.yollpoll.framework.widgets.list.BaseViewHolder
import com.yollpoll.myframework.R

/**
 * Created by spq on 2021/5/11
 */
class MyPagingDataAdapter(private val layoutId: Int,
                          private val variableId: Int?,
                          private val placeHolder: Boolean = false,
                          diffCallback: DiffUtil.ItemCallback<PagingItem>)

    : PagingDataAdapter<PagingItem, BaseViewHolder<PagingItem>>(diffCallback) {

    lateinit var context: Context

    override fun onBindViewHolder(holder: BaseViewHolder<PagingItem>, position: Int) {
        val item = getItem(position)
        if (null != item) {
            holder.bind(item)
        } else if (placeHolder) {
            holder.clear()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<PagingItem> {
        context = parent.context
        val binding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(context), layoutId, parent, false)
        return BaseViewHolder(binding, variableId)
    }
}