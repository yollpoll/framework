package com.yollpoll.framework.net.websocket.msgstrategy;


import java.util.HashMap;

/**
 * Created by spq on 2021/1/6
 */
public enum BackgroudMsgEnum {
    ;
    private MessageStrategy strategy;

    BackgroudMsgEnum(MessageStrategy strategy) {
        this.strategy = strategy;
    }

    public static HashMap<String, MessageStrategy> getAllMsgStrategy() {
        HashMap<String, MessageStrategy> map = new HashMap<>();
        for (BackgroudMsgEnum msgEnum : BackgroudMsgEnum.values()) {
            map.put(msgEnum.strategy.getMsgId(), msgEnum.strategy);
        }
        return map;
    }
}
