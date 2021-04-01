package com.yollpoll.myframework.model;

import androidx.databinding.Bindable;

import com.yollpoll.framework.base.BaseViewModel;

/**
 * Created by spq on 2020-06-02
 */
public class TestDatabindingModel extends BaseViewModel {
    @Bindable
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(com.yollpoll.myframework.BR.name);
    }
}
