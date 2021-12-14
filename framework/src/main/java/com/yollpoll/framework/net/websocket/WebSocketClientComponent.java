package com.yollpoll.framework.net.websocket;


import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketException;
import com.yollpoll.arch.log.LogUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by wangqian on 2019/1/17.
 */

public class WebSocketClientComponent {
    private static final String TAG = "WebSocketClientComponen";
    NVWebSocketClient nWebSocketClient;
    ResponseDispatch nResponseDispatch;
    RequestDispatch nRequestDispatch;
    WebSocketComponentConnectListener nWebSocketComponentConnectListener;

    public WebSocketClientComponent() {
        nWebSocketClient = new NVWebSocketClient();
        nRequestDispatch = new RequestDispatch();
        nRequestDispatch.setNVWebSocketClient(nWebSocketClient);
        nResponseDispatch = new ResponseDispatch();
        setWebSocketListener();
        TimeOut.addClientComponent(this);
    }

    private void setWebSocketListener() {
        nWebSocketClient.setWebSocketListener(new NVWebSocketClient.WebSocketListener() {
            public void onConnectError(WebSocketException exception) {
                if (nWebSocketComponentConnectListener != null) {
                    nWebSocketComponentConnectListener.onConnectError(WebSocketClientComponent.this, exception);
                }
            }

            public void onDisconnected() {
                if (nWebSocketComponentConnectListener != null) {
                    nWebSocketComponentConnectListener.onDisconnected(WebSocketClientComponent.this);
                }
            }

            @Override
            public void onConnected(WebSocket websocket, Map<String, List<String>> headers) throws Exception {
                // 提交缓存的消息
                nRequestDispatch.sendCacheMsgs();
                if (nWebSocketComponentConnectListener != null) {
                    nWebSocketComponentConnectListener.onConnected(WebSocketClientComponent.this, headers);
                }
            }

            @Override
            public void onTextMessage(WebSocket websocket, String text) throws Exception {
                LogUtils.i(TAG,text);
                nResponseDispatch.dispatch(text);
            }
        });
    }

    public NVWebSocketClient getWebSocketClient() {
        return nWebSocketClient;
    }

    public void setWebSocketClient(NVWebSocketClient webSocketClient) {
        this.nWebSocketClient = webSocketClient;
    }

    public ResponseDispatch getResponseDispatch() {
        return nResponseDispatch;
    }


    public RequestDispatch getRequestDispatch() {
        return nRequestDispatch;
    }

    /**
     * 设置推送监听
     */
    public void setOnReceiveNotifyListener(OnReceiveNotifyListener onReceiveNotifyListener) {
        nResponseDispatch.setOnReceiveNotifyListener(onReceiveNotifyListener);
    }

    public WebSocketComponentConnectListener getWebSocketComponentConnectListener() {
        return nWebSocketComponentConnectListener;
    }

    /**
     * 设置websocket连接情况监听
     *
     * @param webSocketComponentConnectListener
     */
    public void setWebSocketComponentConnectListener(WebSocketComponentConnectListener webSocketComponentConnectListener) {
        nWebSocketComponentConnectListener = webSocketComponentConnectListener;
    }
}
