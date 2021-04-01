package com.yollpoll.myframework;

import com.yollpoll.fast.FastViewModel;
import com.yollpoll.framework.annotation.PreExecute;
import com.yollpoll.framework.mvvm.bindingwrapper.command.BindingAction;
import com.yollpoll.framework.mvvm.bindingwrapper.command.BindingCommand;

/**
 * Created by spq on 2021/2/13
 */
public class MainViewModel extends FastViewModel {

    @PreExecute(MR.MainActivity_preMethod)
    public BindingCommand onFloatingClick =
            BindingCommand.build(() -> sendMessage(MR.MainActivity_onMessage, "message"));
}
