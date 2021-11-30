package com.yollpoll.arch.base;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.yollpoll.arch.annotation.handler.AnnotationHandler;
import com.yollpoll.arch.annotation.handler.FieldAnnotationHandler;
import com.yollpoll.arch.annotation.handler.MethodAnnotationHandler;
import com.yollpoll.arch.annotation.handler.PermissionHandler;
import com.yollpoll.arch.annotation.handler.PreExtraAnnotationHandler;
import com.yollpoll.arch.log.LogUtils;
import com.yollpoll.arch.permission.PermissionTool;
import com.yollpoll.arch.permission.RequestPermissionListener;
import com.yollpoll.arch.util.ToastUtil;


/**
 * Created by spq on 2020-06-02
 */
public abstract class BaseActivity<BIND extends ViewDataBinding, VM extends BaseViewModel> extends AppCompatActivity implements IBaseView<BIND, VM>, RequestPermissionListener {
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
        mPermissionTool = PermissionHandler.handlePermission(this);
    }


    private void initMVVM() {
        getViewModel();
        getViewDataBinding();
        initDataBinding();
        if (null != mViewModel) {
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
            ViewModelStoreOwner owner = this;
            ViewModelProvider.AndroidViewModelFactory factory = new ViewModelProvider.AndroidViewModelFactory(getApplication());
            Class clz = AnnotationHandler.getViewModelClass(this);
            mViewModel = (VM) new ViewModelProvider(owner, factory).get(clz);

//            mViewModel= (VM) new ViewModelProvider(this,new ViewModelProvider.AndroidViewModelFactory(BaseApplication.getINSTANCE())).get(AnnotationHandler.getViewModelClass(this));
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
        ToastUtil.showLongToast("已经授权:" + permissions[0]);
    }

    @Override
    public void onPermissionDenied(String[] permissions) {

    }
}
