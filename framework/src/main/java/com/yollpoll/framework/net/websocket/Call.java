package com.yollpoll.framework.net.websocket;

import java.lang.reflect.Type;

/**
 * Created by wangqian on 2019/1/16.
 */

public interface Call<T> {

    void enqueue(OnResponse<T> response);

    void enqueueNoCache(OnResponse<T> response);

    void enqueue();

    void enqueueNoCache();

    OnResponse<T> getResponse();

    Type getReturnType();

    void finishRequest();
}