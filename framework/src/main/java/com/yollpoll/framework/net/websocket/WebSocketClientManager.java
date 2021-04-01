package com.yollpoll.framework.net.websocket;

/**
 * Created by wangqian on 2019/1/16.
 */
public class WebSocketClientManager {

    private static WebSocketClientManager webSocketClientManager = new WebSocketClientManager();
    private WebSocketClientComponent intelWebSocketClientComponent = new WebSocketClientComponent();

    private WebSocketClientManager() {
        intelWebSocketClientComponent.setWebSocketComponentConnectListener(new IntelComponentConnectListener());
    }

    public static WebSocketClientManager getInstance() {
        return webSocketClientManager;
    }

    public WebSocketClientComponent getIntelWebSocketClientComponent() {
        return intelWebSocketClientComponent;
    }

    public void  connectIntelWebSocket(String url) {
        intelWebSocketClientComponent.getWebSocketClient().connect(url);
        intelWebSocketClientComponent.getWebSocketClient().setPingInterval(60000);
        intelWebSocketClientComponent.getWebSocketClient().setPongInterval(60000);
    }

    public void disConnectIntelWebSocket() {
        intelWebSocketClientComponent.getWebSocketClient().disconnect();
    }
}