package com.yollpoll.myframework.ui.pagingdemo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yollpoll.framework.annotation.ContentView
import com.yollpoll.framework.annotation.Extra
import com.yollpoll.framework.annotation.ViewModel
import com.yollpoll.framework.base.BaseActivity
import com.yollpoll.framework.log.LogUtils
import com.yollpoll.framework.utils.ToastUtil
import com.yollpoll.myframework.R
import com.yollpoll.myframework.databinding.ActivityPagingBinding
import com.yollpoll.myframework.databinding.ItemPagingDataBinding
private const val TAG = "PagingDemoActivity"

/**
 * Created by spq on 2021/1/19
 */
@ContentView(R.layout.activity_paging)
@ViewModel(PagingVM::class)
public class PagingDemoActivity : BaseActivity<ActivityPagingBinding, PagingVM>() {
    @Extra
    private lateinit var key: String

    companion object {
        fun gotoPagingDemo(context: Context) {
            var intent = Intent(context, PagingDemoActivity::class.java)
            context.startActivity(intent)
        }
    }

    //    lateinit var mViewModel: PagingVM
    val mAdapter = MyAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        mViewModel = ViewModelProvider(this).get(PagingVM::class.java)
//        val binding = ActivityPagingBinding.inflate(layoutInflater)
//        setContentView(binding.root)

        mDataBinding.rvData.adapter = mAdapter
        mDataBinding.rvData.layoutManager = LinearLayoutManager(this)
        initLiveData()
        ToastUtil.showLongToast(key)
    }

    fun initLiveData() {
        mViewModel.data.observe(this, Observer { mAdapter.submitList(it) })
    }


    /**
     * listadapter需要的
     */
    class MyDiffCallback : DiffUtil.ItemCallback<DataItem>() {
        override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return newItem == oldItem
        }

    }

    /**
     * listadapter
     */
    class MyAdapter : PagedListAdapter<DataItem, RecyclerView.ViewHolder>(MyDiffCallback()) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_paging_data, parent, false))
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            if (holder is ViewHolder) {
                if (holder.binding is ItemPagingDataBinding) {
                    holder.binding.bean = getItem(position)
                    holder.binding.executePendingBindings()
                }
            }
        }


        class ViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

        }
    }
}