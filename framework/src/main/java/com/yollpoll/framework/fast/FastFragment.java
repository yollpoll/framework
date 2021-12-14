package com.yollpoll.framework.fast;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;

import com.yollpoll.arch.base.BaseFragment;
import com.yollpoll.framework.bean.ToastBean;
import com.yollpoll.framework.utils.ToastUtil;

/**
 * Created by spq on 2021/2/13
 */
public abstract class FastFragment<BIND extends ViewDataBinding, VM extends FastViewModel> extends BaseFragment<BIND, VM> {
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initLD();
    }

    private void initLD() {
        mViewModel.getToastLD().observe(getViewLifecycleOwner(), toastBean -> {
            if (toastBean.getDuration() == ToastBean.Duration.SHORT) {
                ToastUtil.showShortToast(toastBean.getMessage());
            } else if (toastBean.getDuration() == ToastBean.Duration.LONG) {
                ToastUtil.showLongToast(toastBean.getMessage());
            }
        });
    }
}
