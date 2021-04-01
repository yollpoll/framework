package com.yollpoll.framework.mvvm.bindingwrapper.command;

import androidx.databinding.ObservableBoolean;

/**
 * Created by spq on 2021/2/28
 */
public class BindingCommand<T> implements BindingAction, BindingConsumer<T> {
    private BindingAction execute;
    private BindingConsumer<T> consumer;
    private BindingFunction<Boolean> canExecute;

    public static BindingCommand build(BindingAction action) {
        return new BindingCommand(action);
    }

    public static <T> BindingCommand<T> build(BindingConsumer<T> consumer) {
        return new BindingCommand<T>(consumer);
    }

    public final ObservableBoolean isViewEnable = new ObservableBoolean(true);

    /**
     * 事件命令前置校验执行函数
     *
     * @param canExecute 直接结果返回true:执行execute逻辑，否则不执行后面的业务逻辑
     */
    public void setPreExecute(BindingFunction<Boolean> canExecute) {
        this.canExecute = canExecute;
    }


    /**
     * 无参数的触发命令
     *
     * @param execute 触发命令
     */
    public BindingCommand(BindingAction execute) {
        this.execute = execute;
    }


    /**
     * 带泛型参数的命令绑定
     *
     * @param execute 触发命令
     */
    public BindingCommand(BindingConsumer<T> execute) {
        this.consumer = execute;
    }

    /**
     * 执行BindingAction命令
     */
    @Override
    public void execute() {
        if (execute != null && canExecute()) {
            execute.execute();
        }
    }

    /**
     * 执行带泛型参数的命令
     *
     * @param parameter 泛型参数
     */
    @Override
    public void execute(T parameter) {
        if (consumer != null && canExecute()) {
            consumer.execute(parameter);
        }
    }

    /**
     * 是否需要执行
     *
     * @return true则执行, 反之不执行
     */
    private boolean canExecute() {
        if (isViewEnable == null && canExecute == null) return true;
        if (isViewEnable != null && !isViewEnable.get()) {
            return false;
        }

        if (canExecute == null) {
            return true;
        }
        Boolean execute = canExecute.execute();
        if (execute == null) return true;
        return execute;
    }
}
