package com.yollpoll.fast;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.yollpoll.fast.bean.ToastBean;
import com.yollpoll.framework.base.BaseFragment;
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
        mViewModel.getToastLD().observe(this, toastBean -> {
            if (toastBean.getDuration() == ToastBean.Duration.SHORT) {
                ToastUtil.showShortToast(toastBean.getMessage());
            } else if (toastBean.getDuration() == ToastBean.Duration.LONG) {
                ToastUtil.showLongToast(toastBean.getMessage());
            }
        });
    }
}
