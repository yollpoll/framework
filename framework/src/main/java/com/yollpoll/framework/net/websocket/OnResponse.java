package com.yollpoll.framework.net.websocket;

/**
 * Created by wangqian on 2019/1/16.
 */

public interface OnResponse<T> {
    void onSuccess(T data, String msgId, String message);

    void onTimeOut();
}
