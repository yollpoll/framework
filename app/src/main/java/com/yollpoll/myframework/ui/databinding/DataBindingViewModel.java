package com.yollpoll.myframework.ui.databinding;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;


import com.yollpoll.framework.fast.FastViewModel;
import com.yollpoll.myframework.model.TestDatabindingModel;

import java.util.logging.Handler;

/**
 * Created by spq on 2020-06-02
 */
public class DataBindingViewModel extends FastViewModel {
    TestDatabindingModel testDatabindingModel=new TestDatabindingModel(getApplication());

    public DataBindingViewModel(@NonNull Application application) {
        super(application);
        initData();
    }
    private void initData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    testDatabindingModel.setName("姓名:yollpoll");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
