package com.yollpoll.myframework.ui.livedatademo;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import kotlinx.coroutines.flow.Flow;

/**
 * Created by spq on 2020-06-11
 */
public class LiveDataViewModule extends ViewModel {
    private MutableLiveData<String> content;
    public MutableLiveData<String> getContent() {
        if(null==content){
            content=new MutableLiveData<>();
        }
        startChangeValue();
        return content;
    }

    private void startChangeValue(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //post 在子线程,set在main线程
                content.postValue("自动修改数据");
            }
        }).start();
    }
}
