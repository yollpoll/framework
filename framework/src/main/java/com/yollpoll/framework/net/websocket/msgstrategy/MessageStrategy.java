package com.yollpoll.framework.net.websocket.msgstrategy;

/**
 * Created by spq on 2021/1/6
 */
public interface MessageStrategy {
    void handle(String msgId, String message);

    String getMsgId();
}
