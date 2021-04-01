package com.yollpoll.framework.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.yollpoll.framework.annotation.handler.AnnotationHandler;
import com.yollpoll.framework.annotation.handler.FieldAnnotationHandler;
import com.yollpoll.framework.annotation.handler.MethodAnnotationHandler;
import com.yollpoll.framework.annotation.handler.PreExtraAnnotationHandler;
import com.yollpoll.framework.log.LogUtils;
import com.yollpoll.framework.mvvm.view.IBaseView;
import com.yollpoll.framework.permission.RequestPermissionListener;

/**
 * Created by spq on 2021/2/13
 */
public abstract class BaseFragment<BIND extends ViewDataBinding, VM extends BaseViewModel> extends Fragment implements IBaseView<BIND, VM>,RequestPermissionListener {
    private static final String TAG = "BaseFragment";
    protected VM mViewModel;
    protected BIND mDataBinding;
    protected int mLayoutId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initMVVM(container);
        initFieldAnnotation();
        initMethodAnnotation();

        return null == getViewDataBinding() ? super.onCreateView(inflater, container, savedInstanceState) :
                getViewDataBinding().getRoot();
    }

    private void initMVVM(ViewGroup viewGroup) {
        getViewModel();
//        getViewDataBinding();
        initDataBinding(viewGroup);
        if (null != mViewModel) {
            PreExtraAnnotationHandler.handlePreExecute(this);
        }
    }

    /**
     * 初始化dataBinding
     * 将view和viewModel绑定到dataBinding
     */
    private void initDataBinding(ViewGroup viewGroup) {
        if (null == mDataBinding && getContentViewId() > 0) {
            mDataBinding = DataBindingUtil.inflate(getLayoutInflater(), getContentViewId(), viewGroup, false);
        }

        //绑定Model
        if (mDataBinding != null) {

            boolean result = AnnotationHandler.setViewModelFieldValueOfViewDataBinding(mDataBinding, mViewModel);
            if (!result) {
                LogUtils.w("ViewDataBinding", "can't invoke the method of " + mDataBinding.getClass().getName() + "#setViewModel,please add the variable of 'viewModel' to " + getResources().getResourceName(getContentViewId()) + ".xml");
            }
            result = AnnotationHandler.setViewFieldValueOfViewDataBinding(mDataBinding, this);
            if (!result) {
                LogUtils.w("ViewDataBinding", "can't invoke the method of " + mDataBinding.getClass().getName() + "#setViewRef,please add the variable of 'viewRef' to " + getResources().getResourceName(getContentViewId()) + ".xml");
            }
        }
    }

    /**
     * 处理field注解
     */
    private void initFieldAnnotation() {
        new FieldAnnotationHandler().dispatch(this);
    }

    /**
     * 处理method注解
     */
    private void initMethodAnnotation() {
        new MethodAnnotationHandler().dispatch(this);
    }

    @Override
    public VM getViewModel() {
        if (null == mViewModel) {
            mViewModel = (VM) new ViewModelProvider(this).get(AnnotationHandler.getViewModelClass(this));
        }
        return mViewModel;
    }

    @Override
    public BIND getViewDataBinding() {
//        if (null == mDataBinding) {
//            if (getContentViewId() > 0) {
//                mDataBinding = DataBindingUtil.setContentView(getContext(), getContentViewId());
//
//            } else {
//                LogUtils.w(TAG, "can't find ContentViewId,please use the annotation of @ContentView ,or override method of getContentViewId() ");
//            }
//        }
        return mDataBinding;
    }

    @Override
    public int getContentViewId() {
        if (mLayoutId == 0) {
            mLayoutId = AnnotationHandler.getLayoutId(this);
        }
        return mLayoutId;
    }

    @Nullable
    @Override
    public Activity getContext() {
        return getActivity();
    }

    @Override
    public RequestPermissionListener getPermissionListener() {
        return this;
    }

    @Override
    public void onShowRationale(String[] permissions) {

    }

    @Override
    public void onPermissionAllowed(String[] permissions) {

    }

    @Override
    public void onPermissionDenied(String[] permissions) {

    }
}
