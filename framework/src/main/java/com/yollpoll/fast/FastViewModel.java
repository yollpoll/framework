package com.yollpoll.fast;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.yollpoll.fast.bean.ToastBean;
import com.yollpoll.framework.base.BaseViewModel;
import com.yollpoll.framework.message.MessageManager;
import com.yollpoll.framework.message.liveeventbus.LiveEventBus;
import com.yollpoll.framework.message.liveeventbus.NonType;

/**
 * Created by spq on 2021/2/13
 */
public abstract class FastViewModel extends BaseViewModel {
    private MutableLiveData<ToastBean> toastLD = new MutableLiveData<>();

    public FastViewModel(@NonNull Application application) {
        super(application);
    }

    public void showShortToast(String message) {
        ToastBean toastBean = new ToastBean(message, ToastBean.Duration.SHORT);
        toastLD.setValue(toastBean);
    }

    public void showLongToast(String message) {
        ToastBean toastBean = new ToastBean(message, ToastBean.Duration.LONG);
        toastLD.setValue(toastBean);
    }

    public LiveData<ToastBean> getToastLD() {
        if (null == toastLD) {
            toastLD = new MutableLiveData<>();
        }
        return toastLD;
    }

    public <T> void sendMessage(String methodName, T data) {
        MessageManager.getInstance().sendMessage(methodName,data);
    }

    public <T> void sendEmptyMessage(String methodName) {
        MessageManager.getInstance().sendMessage(methodName,NonType.INSTANCE);
//        LiveEventBus.use(methodName, NonType.class).post(NonType.INSTANCE);
    }
}
