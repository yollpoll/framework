package com.yollpoll.myframework.ui.databinding;

import androidx.lifecycle.ViewModel;


import com.yollpoll.myframework.model.TestDatabindingModel;

import java.util.logging.Handler;

/**
 * Created by spq on 2020-06-02
 */
public class DataBindingViewModel extends ViewModel {
    TestDatabindingModel testDatabindingModel=new TestDatabindingModel();
    public DataBindingViewModel() {
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
