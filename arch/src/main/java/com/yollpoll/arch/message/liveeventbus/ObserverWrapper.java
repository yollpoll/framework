package com.yollpoll.arch.message.liveeventbus;

import androidx.lifecycle.Observer;

/**
 * Created by spq on 2021/2/14
 * eventBus观察者wrapper
 */
public abstract class ObserverWrapper<T> {
    //每个观察者都有自己的序号
    private int uid;
    private Observer<EventWrapper<T>> observer;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public Observer<EventWrapper<T>> getObserver() {
        return observer;
    }

    public void setObserver(Observer<EventWrapper<T>> observer) {
        this.observer = observer;
    }

    public abstract boolean isSticky();

    public abstract void onChanged(T value);

    public abstract boolean mainThread();

}
