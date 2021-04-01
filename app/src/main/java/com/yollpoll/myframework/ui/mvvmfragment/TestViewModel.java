package com.yollpoll.myframework.ui.mvvmfragment;

import com.yollpoll.fast.FastViewModel;
import com.yollpoll.framework.base.BaseViewModel;
import com.yollpoll.framework.mvvm.bindingwrapper.command.BindingCommand;

/**
 * Created by spq on 2021/3/3
 */
public class TestViewModel extends FastViewModel {
    public BindingCommand bindingCommand=BindingCommand.build(()->{
        showLongToast("onClick by bindingCommand");
    });
}
