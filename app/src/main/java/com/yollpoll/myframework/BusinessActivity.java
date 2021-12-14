package com.yollpoll.myframework;

import androidx.databinding.ViewDataBinding;

import com.yollpoll.framework.fast.FastActivity;
import com.yollpoll.framework.fast.FastViewModel;


/**
 * Created by spq on 2021/3/3
 */
public abstract class BusinessActivity<BIND extends ViewDataBinding, VM extends FastViewModel> extends FastActivity<BIND, VM> {
}
