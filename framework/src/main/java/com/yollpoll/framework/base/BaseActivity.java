package com.yollpoll.framework.base;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

import com.yollpoll.framework.annotation.handler.AnnotationHandler;
import com.yollpoll.framework.annotation.handler.FieldAnnotationHandler;
import com.yollpoll.framework.annotation.handler.MethodAnnotationHandler;
import com.yollpoll.framework.annotation.handler.PermissionHandler;
import com.yollpoll.framework.annotation.handler.PreExtraAnnotationHandler;
import com.yollpoll.framework.log.LogUtils;
import com.yollpoll.framework.mvvm.view.IBaseView;
import com.yollpoll.framework.permission.PermissionTool;
import com.yollpoll.framework.permission.RequestPermissionListener;
import com.yollpoll.framework.utils.ToastUtil;

/**
 * Created by spq on 2020-06-02
 */
public abstract class BaseActivity<BIND extends ViewDataBinding, VM extends BaseViewModel> extends AppCompatActivity implements IBaseView<BIND, VM> , RequestPermissionListener {
    private static final String TAG = "BaseActivity";
    protected VM mViewModel;
    protected BIND mDataBinding;
    protected int mLayoutId;
    protected PermissionTool mPermissionTool;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initMVVM();
        initFieldAnnotation();
        initMethodAnnotation();
        mPermissionTool= PermissionHandler.handlePermission(this);
    }


    private void initMVVM() {
        getViewModel();
        getViewDataBinding();
        initDataBinding();
        if(null!=mViewModel){
            PreExtraAnnotationHandler.handlePreExecute(this);
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

    /**
     * 初始化dataBinding
     * 将view和viewModel绑定到dataBinding
     */
    private void initDataBinding() {
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

    @Override
    public VM getViewModel() {
        if (null == mViewModel) {
            mViewModel= (VM) new ViewModelProvider(this,new ViewModelProvider.AndroidViewModelFactory(BaseApplication.getINSTANCE())).get(AnnotationHandler.getViewModelClass(this));
//            mViewModel = (VM) new ViewModelProvider(this).get(AnnotationHandler.getViewModelClass(this));
        }
        return mViewModel;
    }

    @Override
    public BIND getViewDataBinding() {
        if (null == mDataBinding) {
            if (getContentViewId() > 0) {
                mDataBinding = DataBindingUtil.setContentView(this, getContentViewId());
            } else {
                LogUtils.w(TAG, "can't find ContentViewId,please use the annotation of @ContentView ,or override method of getContentViewId() ");
            }
        }
        return mDataBinding;
    }

    @Override
    public int getContentViewId() {
        if (mLayoutId == 0) {
            mLayoutId = AnnotationHandler.getLayoutId(this);
        }
        return mLayoutId;
    }

    @Override
    public Activity getContext() {
        return this;
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
        ToastUtil.showLongToast("已经授权:"+permissions[0]);
    }

    @Override
    public void onPermissionDenied(String[] permissions) {

    }
}
