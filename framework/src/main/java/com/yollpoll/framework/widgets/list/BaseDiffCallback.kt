package com.yollpoll.framework.widgets.list

import androidx.recyclerview.widget.DiffUtil

/**
 * Created by spq on 2021/6/16
 */
abstract class BaseDiffCallback<T>(private val oldItems: List<T>, private val newItems: List<T>) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldItems.size
    }

    override fun getNewListSize(): Int {
        return newItems.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return areItemsTheSame(oldItems[oldItemPosition], newItems[newItemPosition])
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return areContentsTheSame(oldItems[oldItemPosition], newItems[newItemPosition])
//        return oldItems[oldItemPosition] == newItems[newItemPosition]
    }

    abstract fun areItemsTheSame(oldItem: T, newItem: T): Boolean
    abstract fun areContentsTheSame(oldItem: T, newItem: T): Boolean
}

abstract class PositionDiffCallback<T>(
    private val oldItems: List<T>,
    private val newItems: List<T>
) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldItems.size
    }

    override fun getNewListSize(): Int {
        return newItems.size
    }
}