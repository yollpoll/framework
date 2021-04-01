package com.yollpoll.framework.net.websocket;

/**
 * Created by wangqian on 2019/1/18.
 */

public interface OnReceiveNotifyListener {

    void onReceiveNotify(String msgId, String msg);
}
