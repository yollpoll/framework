package com.yollpoll.myframework.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;

import com.yollpoll.arch.base.BaseViewModel;
import com.yollpoll.framework.fast.FastViewModel;


/**
 * Created by spq on 2020-06-02
 */
public class TestDatabindingModel extends FastViewModel {
    @Bindable
    private String name;

    public TestDatabindingModel(@NonNull Application application) {
        super(application);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(com.yollpoll.myframework.BR.name);
    }
}
