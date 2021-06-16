package com.yollpoll.framework.widgets.list

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.AsyncPagedListDiffer
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by spq on 2021/4/29
 */

/**
 * 基础抽象类实现类
 * @param T
 * @property layoutId Int
 * @property variableId Int?
 * @property placeHolder Boolean
 * @constructor
 */
class MyPageListAdapter<T:Any>(
        private val layoutId: Int,
        private val variableId: Int? = null,
        private val placeHolder: Boolean = false,
        callBack: DiffUtil.ItemCallback<T>) : BasePageListAdapter<T>(layoutId, variableId, placeHolder, callBack)

/**
 * 基于PagedList的baseAdapter
 * @param T 数据类型
 * @property layoutId Int 布局文件
 * @property variableId Int? br id
 * @property mDiffer AsyncPagedListDiffer<T> diffUtil回调
 * @param placeHolder 是否采用占位显示
 */
abstract class BasePageListAdapter<T:Any>(
        private val layoutId: Int,
        private val variableId: Int? = null,
        private val placeHolder: Boolean = false,
        callBack: DiffUtil.ItemCallback<T>) : PagedListAdapter<T, BaseViewHolder<T>>(callBack) {

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
                LayoutInflater.from(parent.context),
                layoutId,
                parent,
                false
        )
        context = parent.context
        return BaseViewHolder<T>(binding, variableId)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        val item = getItem(position)
        if (null != item) {
            holder.bind(item)
        } else if (placeHolder) {
            holder.clear()
        }
    }
}

/**
 * 基于普通list的adapter
 * @param MODEL 数据类型
 * @property layoutId Int 布局文件
 * @property mData ArrayList<MODEL> 列表数据
 * @property mContext Context context
 */
abstract class BaseAdapter<MODEL>(val layoutId: Int, val mData: ArrayList<MODEL>) : RecyclerView.Adapter<BaseViewHolder<MODEL>>() {

    lateinit var mContext: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<MODEL> {
        mContext = parent.context
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
                LayoutInflater.from(parent.context),
                layoutId,
                parent,
                false
        )
        return BaseViewHolder(binding, null)
    }

    override fun getItemCount(): Int {
        return mData.size
    }
}

/**
 * 基于databing的viewHolder
 * @param T 数据类型
 * @property binding ViewDataBinding binding
 * @property variableId Int? T对应的BR_id
 */
open class BaseViewHolder<T>(val binding: ViewDataBinding, val variableId: Int?) :
        RecyclerView.ViewHolder(binding.root) {

    fun bind(t: T) {
        variableId?.let {
            binding.setVariable(variableId, t)
            binding.executePendingBindings()
        }
    }

    /**
     * 占位显示
     */
    fun clear() {

    }

}
