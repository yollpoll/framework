package com.yollpoll.framework.net.websocket.msgstrategy;

import android.text.TextUtils;

import java.util.Map;

/**
 * Created by spq on 2021/1/6
 */
public class MsgStrategyManager {
    public static void handle(String msgId, String message) {
        if (TextUtils.isEmpty(msgId))
            return;
        Map<String, MessageStrategy> strategyMap = MsgEnum.getAllMsgStrategy();
        MessageStrategy strategy = strategyMap.get(msgId);
        if (null != strategy) {
            strategy.handle(msgId, message);
        }
    }
//    public static void asyncHandle(String msgId, String message) {
//        if (TextUtils.isEmpty(msgId))
//            return;
//        Map<String, MessageStrategy> strategyMap = BackgroudMsgEnum.getAllMsgStrategy();
//        MessageStrategy strategy = strategyMap.get(msgId);
//        if (null != strategy) {
//            strategy.handle(msgId, message);
//        }
//    }
}
