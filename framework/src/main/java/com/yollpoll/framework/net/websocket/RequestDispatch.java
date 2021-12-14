package com.yollpoll.framework.net.websocket;



import com.yollpoll.arch.log.LogUtils;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by wangqian on 2019/1/17.
 */
public class RequestDispatch {

    private static final String TAG = "RequestDispatch";

    BlockingQueue<RequestMessage> cacheQueue = new LinkedBlockingDeque<>();
    CopyOnWriteArrayList<RequestMessage> processingMsgList = new CopyOnWriteArrayList<>();
    private NVWebSocketClient nvWebSocketClient;

    public void setNVWebSocketClient(NVWebSocketClient webSocketClient) {
        nvWebSocketClient = webSocketClient;
    }

    public String createMsgId() {
        return UUID.randomUUID().toString();
    }

    public void sendMsgNoReturn(RequestMessage requestMessage) {
        sendMsgNoReturn(requestMessage, true);
    }

    public void sendMsgNoReturn(RequestMessage requestMessage, boolean isUseCache) {
        LogUtils.i(TAG, requestMessage.getRealMsg());
        if (nvWebSocketClient.isConnectSuccess()) {
            nvWebSocketClient.sendMessage(requestMessage.getRealMsg());
        } else if (isUseCache) {
            cacheQueue.offer(requestMessage);
        }
    }

    public void sendCacheMsgs() {
        while (cacheQueue.size() > 0) {
            RequestMessage msg = cacheQueue.poll();
            sendMsg(msg);
        }
    }

    public void sendMsg(RequestMessage requestMessage) {
        sendMsg(requestMessage, true);
    }

    public void sendMsg(RequestMessage requestMessage, boolean isUseCache) {
        TimeOut.startCheckTimeOut();
        LogUtils.i(TAG, requestMessage.getRealMsg());
        if (nvWebSocketClient.isConnectSuccess()) {
            nvWebSocketClient.sendMessage(requestMessage.getRealMsg());
            processingMsgList.add(requestMessage);
        } else if (isUseCache) {
            cacheQueue.offer(requestMessage);
        }
    }

    public void finishRequest(String msgUUId, Call call) {
        RequestMessage requestMessage = new RequestMessage(msgUUId);
        cacheQueue.remove(requestMessage);
        processingMsgList.remove(requestMessage);
    }
}