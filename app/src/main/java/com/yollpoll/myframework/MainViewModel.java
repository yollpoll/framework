package com.yollpoll.myframework;

import android.app.Application;

import androidx.annotation.NonNull;

import com.yollpoll.fast.FastViewModel;
import com.yollpoll.framework.annotation.PreExecute;
import com.yollpoll.framework.mvvm.bindingwrapper.command.BindingAction;
import com.yollpoll.framework.mvvm.bindingwrapper.command.BindingCommand;
import com.yollpoll.myframework.ui.test.MR;

/**
 * Created by spq on 2021/2/13
 */
public class MainViewModel extends FastViewModel {

    @PreExecute(MR.MainActivity_preMethod)
    public BindingCommand onFloatingClick =
            BindingCommand.build(() -> sendMessage(MR.MainActivity_onMessage, "message"));

    public MainViewModel(@NonNull Application application) {
        super(application);
    }
}
