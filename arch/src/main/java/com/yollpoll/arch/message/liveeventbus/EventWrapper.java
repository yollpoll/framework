package com.yollpoll.arch.message.liveeventbus;

import androidx.annotation.NonNull;

/**
 * Created by spq on 2021/2/14
 */
public class EventWrapper<T> {
    //每个被观察的事件都有一个序号，只有生产的事件数据在观察者加入后才通知到观察者
    //即事件序号要大于观察者序号
    private int uid;
    @NonNull
    private T value;

    public EventWrapper(int uid, T value) {
        this.uid = uid;
        this.value = value;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
