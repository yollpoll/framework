package com.yollpoll.framework.fast;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.yollpoll.arch.base.BaseViewModel;
import com.yollpoll.arch.message.MessageManager;
import com.yollpoll.arch.message.liveeventbus.NonType;
import com.yollpoll.framework.bean.ToastBean;


/**
 * Created by spq on 2021/2/13
 */
public abstract class FastViewModel extends BaseViewModel {
    private MutableLiveData<ToastBean> toastLD = new MutableLiveData<>();
    private MutableLiveData<Boolean> loadingLD = new MutableLiveData<>();
    private MutableLiveData<Boolean> finishLD = new MutableLiveData<>();

    public FastViewModel(@NonNull Application application) {
        super(application);
    }


    /**
     * 检查并执行代码
     *
     * @param runnable 可执行代码
     */
    private void checkThread(Runnable runnable) {
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            runnable.run();
        } else {
            new Handler(Looper.getMainLooper()).post(runnable);
        }
    }

    /**
     * 关闭当前view
     */
    public void finish() {
        checkThread(() -> {
            finishLD.setValue(true);
        });
    }

    public void showShortToast(String message) {
        ToastBean toastBean = new ToastBean(message, ToastBean.Duration.SHORT);
        checkThread(() -> {
            toastLD.setValue(toastBean);
        });

//        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
//            toastLD.setValue(toastBean);
//        } else {
//            toastLD.postValue(toastBean);
//        }
    }

    public void showLongToast(String message) {
        ToastBean toastBean = new ToastBean(message, ToastBean.Duration.LONG);
        checkThread(() -> {
            toastLD.setValue(toastBean);
        });
//        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
//            toastLD.setValue(toastBean);
//        } else {
//            toastLD.postValue(toastBean);
//        }
    }

    public void showLoading() {
        checkThread(() -> {
            loadingLD.setValue(true);
        });
//        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
//            //当前为主线程
//            loadingLD.setValue(true);
//        } else {
//            loadingLD.postValue(true);
//        }
    }

    public void hideLoading() {
        checkThread(() -> {
            loadingLD.setValue(false);
        });
//        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
//            //当前为主线程
//            loadingLD.setValue(false);
//        } else {
//            loadingLD.postValue(false);
//        }
    }

    public LiveData<ToastBean> getToastLD() {
        if (null == toastLD) {
            toastLD = new MutableLiveData<>();
        }
        return toastLD;
    }

    public MutableLiveData<Boolean> getLoadingLD() {
        return loadingLD;
    }

    public MutableLiveData<Boolean> getFinishLD() {
        return finishLD;
    }

    public <T> void sendMessage(String methodName, T data) {
        MessageManager.getInstance().sendMessage(methodName, data);
    }

    public void sendEmptyMessage(String methodName) {
        MessageManager.getInstance().sendMessage(methodName, NonType.INSTANCE);
    }

    /****************************http********************************/
   public void getHttpService(){

   }

}
