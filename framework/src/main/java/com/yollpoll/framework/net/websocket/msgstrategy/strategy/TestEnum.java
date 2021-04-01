package com.yollpoll.framework.net.websocket.msgstrategy.strategy;

import com.yollpoll.framework.message.liveeventbus.LiveEventBus;
import com.yollpoll.framework.net.websocket.msgstrategy.MessageStrategy;

/**
 * Created by spq on 2021/2/23
 */
public enum TestEnum implements MessageStrategy {
    INSTANCE;

    @Override
    public void handle(String msgId, String message) {
        LiveEventBus.use(msgId, String.class).post(message);
    }

    @Override
    public String getMsgId() {
        return "test";
    }
}
