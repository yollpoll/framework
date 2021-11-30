package com.yollpoll.arch.message;


import com.yollpoll.arch.message.liveeventbus.LiveEventBus;

/**
 * Created by spq on 2021/2/28
 */
public class MessageManager {
    private static final Object nLock = new Object();
    private static MessageManager nInstance;

    /**
     * 在使用本地消息的时候需要初始化实例
     *
     * @return
     */
    private static MessageManager initInstance() {
        synchronized (nLock) {
            if (nInstance == null) {
                nInstance = new MessageManager();
            }
            return nInstance;
        }
    }

    public static MessageManager getInstance() {
        if (nInstance == null) {
            return initInstance();
        }
        return nInstance;
    }


    private MessageManager() {

    }

    /**
     * 发送无数据消息
     *
     * @param action 消息来源
     * @return
     */
    public void sendEmptyMessage(String action) {
        sendMessage(action, null);
    }

    /**
     * 发送有数据消息
     *
     * @param action
     * @param data
     */
    public <T> void sendMessage(String action, T data) {
        LiveEventBus.use(action, Object.class).post(data);
    }
}
