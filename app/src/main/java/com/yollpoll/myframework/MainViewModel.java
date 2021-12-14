package com.yollpoll.myframework;

import android.app.Application;

import androidx.annotation.NonNull;

import com.yollpoll.arch.annotation.PreExecute;
import com.yollpoll.arch.binding.bindingwrapper.command.BindingCommand;
import com.yollpoll.framework.fast.FastViewModel;
import com.yollpoll.myframework.ui.fragment.MR;


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
