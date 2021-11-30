package com.yollpoll.arch.binding.bindingwrapper.command;

/**
 * Created by spq on 2021/2/28
 */
interface BindingConsumer<T> {
    void execute(T input);
}
