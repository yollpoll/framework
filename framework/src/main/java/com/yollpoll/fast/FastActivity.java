package com.yollpoll.fast;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;

import com.yollpoll.fast.bean.ToastBean;
import com.yollpoll.framework.base.BaseActivity;
import com.yollpoll.framework.base.BaseViewModel;
import com.yollpoll.framework.utils.ToastUtil;

/**
 * Created by spq on 2021/2/13
 */
public abstract class FastActivity<BIND extends ViewDataBinding, VM extends FastViewModel> extends BaseActivity<BIND, VM> {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLD();
    }

    private void initLD() {
        mViewModel.getToastLD().observe(this, toastBean -> {
            if (toastBean.getDuration() == ToastBean.Duration.SHORT) {
                ToastUtil.showShortToast(toastBean.getMessage());
            } else if (toastBean.getDuration() == ToastBean.Duration.LONG) {
                ToastUtil.showLongToast(toastBean.getMessage());
            }
        });
    }

}
