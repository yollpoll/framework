package com.yollpoll.framework.net.websocket;

import com.neovisionaries.ws.client.WebSocketException;
import com.yollpoll.framework.log.LogUtils;

import java.util.List;
import java.util.Map;

public class IntelComponentConnectListener implements WebSocketComponentConnectListener {

    private static final String TAG = "WebSocket";

    // 标识是否断开连接
    private boolean isHappenedError = false;

    @Override
    public void onConnectError(WebSocketClientComponent webSocketClientComponent, WebSocketException exception) {
        if (!isHappenedError) {
            isHappenedError = true;
            LogUtils.w(TAG, "WebSocket连接错误，尝试重新连接。" + exception.getMessage());
        }
    }

    @Override
    public void onDisconnected(WebSocketClientComponent webSocketClientComponent) {
        if (!isHappenedError) {
            isHappenedError = true;
            LogUtils.i(TAG, "WebSocket断开连接");
        }
    }

    @Override
    public void onConnected(WebSocketClientComponent webSocketClientComponent, Map<String, List<String>> headers) {
        // 重置
        isHappenedError = false;
        LogUtils.i(TAG, "WebSocket连接成功");
    }
}