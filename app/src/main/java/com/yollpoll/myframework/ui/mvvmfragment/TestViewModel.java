package com.yollpoll.myframework.ui.mvvmfragment;

import android.app.Application;

import androidx.annotation.NonNull;

import com.yollpoll.arch.binding.bindingwrapper.command.BindingCommand;
import com.yollpoll.framework.fast.FastViewModel;

/**
 * Created by spq on 2021/3/3
 */
public class TestViewModel extends FastViewModel {
    public BindingCommand bindingCommand=BindingCommand.build(()->{
        showLongToast("onClick by bindingCommand");
    });

    public TestViewModel(@NonNull Application application) {
        super(application);
    }
}
