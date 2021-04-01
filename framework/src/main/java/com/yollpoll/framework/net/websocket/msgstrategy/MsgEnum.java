package com.yollpoll.framework.net.websocket.msgstrategy;


import com.yollpoll.framework.net.websocket.msgstrategy.strategy.TestEnum;

import java.util.HashMap;

/**
 * Created by spq on 2021/1/6
 */
public enum MsgEnum {
    TEST(TestEnum.INSTANCE),
    ;
    private MessageStrategy strategy;

    MsgEnum(MessageStrategy strategy) {
        this.strategy = strategy;
    }

    public static HashMap<String, MessageStrategy> getAllMsgStrategy() {
        HashMap<String, MessageStrategy> map = new HashMap<>();
        for (MsgEnum msgEnum : MsgEnum.values()) {
            map.put(msgEnum.strategy.getMsgId(), msgEnum.strategy);
        }
        return map;
    }
}
