package com.yollpoll.framework.net.websocket;

import com.neovisionaries.ws.client.WebSocketException;

import java.util.List;
import java.util.Map;

/**
 * Created by wangqian on 2019/3/7.
 */

public interface WebSocketComponentConnectListener {

    void onConnectError(WebSocketClientComponent webSocketClientComponent, WebSocketException exception);

    void onDisconnected(WebSocketClientComponent webSocketClientComponent);

    void onConnected(WebSocketClientComponent webSocketClientComponent, Map<String, List<String>> headers) throws Exception;

}