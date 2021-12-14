package com.yollpoll.framework.fast;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;

import com.yollpoll.arch.base.BaseActivity;
import com.yollpoll.framework.bean.ToastBean;
import com.yollpoll.framework.utils.ToastUtil;
import com.yollpoll.framework.widgets.LoadingDialog;

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
        mViewModel.getToastLD().observe(this, this::showToast
        );
        mViewModel.getLoadingLD().observe(this, show -> {
            if (show) {
                showLoading();
            } else {
                hideLoading();
            }
        });
        mViewModel.getFinishLD().observe(this, finish -> {
            if (finish) {
                getContext().finish();
            }
        });
    }

    public void showLoading() {
        LoadingDialog.Companion.showLoading(this);
    }

    public void hideLoading() {
        LoadingDialog.Companion.hide();
    }

    protected void showToast(ToastBean toastBean) {
        if (toastBean.getDuration() == ToastBean.Duration.LONG) {
            ToastUtil.showLongToast(toastBean.getMessage());
        } else {
            ToastUtil.showShortToast(toastBean.getMessage());
        }
    }

}
