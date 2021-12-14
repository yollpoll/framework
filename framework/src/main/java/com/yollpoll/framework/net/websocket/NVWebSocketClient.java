package com.yollpoll.framework.net.websocket;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.neovisionaries.ws.client.WebSocketFrame;
import com.yollpoll.arch.log.LogUtils;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by wangqian on 2019/1/16.
 */

public class NVWebSocketClient {
    private static final int DEFAULT_SOCKET_CONNECTTIMEOUT = 5000;
    private static final int DEFAULT_SOCKET_RECONNECTINTERVAL = 5000;
    private static final int FRAME_QUEUE_SIZE = 5;
    private static String TAG = "NVWebSocketClient";

    private WebSocketListener mWebSocketListener;
    private WebSocketFactory mWebSocketFactory;
    private WebSocket mWebSocket;
    private boolean isCloseGracefully = false;
    private volatile ConnectStatus mConnectStatus = ConnectStatus.CONNECT_DISCONNECT;
    private Timer mReconnectTimer = new Timer("websocketReconnectTimer");
    private TimerTask mReconnectTimerTask;

    private String mUri;

    public NVWebSocketClient() {
        this(null, DEFAULT_SOCKET_CONNECTTIMEOUT);
    }

    public NVWebSocketClient(String uri, int timeout) {
        mUri = uri;
        mWebSocketFactory = new WebSocketFactory().setConnectionTimeout(timeout);
    }

    public NVWebSocketClient(String uri) {
        this(uri, DEFAULT_SOCKET_CONNECTTIMEOUT);
    }

    private static void printStackTrace() {
        StackTraceElement[] stackElements = new Throwable().getStackTrace();
        if (stackElements != null) {
            for (int i = 0; i < stackElements.length; i++) {
                LogUtils.d(TAG, "" + stackElements[i]);
            }
        }
    }

    public void setWebSocketListener(WebSocketListener webSocketListener) {
        mWebSocketListener = webSocketListener;
    }

    public void connect(String uri) {
        mUri = uri;
        connect();
    }

    public void setUrl(String uri) {
        mUri = uri;
    }

    public void connect() {
        isCloseGracefully = false;
        try {
            mWebSocket = mWebSocketFactory.createSocket(mUri)
                    .setFrameQueueSize(FRAME_QUEUE_SIZE)//设置帧队列最大值为5
                    .setMissingCloseFrameAllowed(false)//设置不允许服务端关闭连接却未发送关闭帧
                    .addListener(new NVWebSocketListener())
                    .connectAsynchronously();
            setConnectStatus(ConnectStatus.CONNECTING);
        } catch (IOException e) {
            LogUtils.w(TAG, "connect error -> " + e.getMessage());
            reconnect();
        }
    }

    public boolean isConnectSuccess() {
        return mConnectStatus == ConnectStatus.CONNECT_SUCCESS;
    }

    public void sendMessage(String msg) {
        if (mWebSocket != null) {
            mWebSocket.sendText(msg);
        }
    }

    public boolean sendBinary(byte[] msg) {
        if (mWebSocket != null && mWebSocket.isOpen() && getConnectStatus() == ConnectStatus.CONNECT_SUCCESS) {
            mWebSocket.sendBinary(msg);
            return true;
        }
        return false;
    }

    public ConnectStatus getConnectStatus() {
        return mConnectStatus;
    }

    private void setConnectStatus(ConnectStatus connectStatus) {
        mConnectStatus = connectStatus;
    }

    public void disconnect() {
        isCloseGracefully = true;
        if (mWebSocket != null) {
            mWebSocket.disconnect();
        }
        TimeOut.stopCheckTimeOut();
        setConnectStatus(null);
    }

    /**
     * @param interval The interval in milliseconds. A negative value is
     *                 regarded as zero.
     */
    public void setPingInterval(long interval) {
        if (mWebSocket != null) {
            mWebSocket.setPingInterval(interval);
        }
    }

    public void setPongInterval(long interval) {
        if (mWebSocket != null) {
            mWebSocket.setPongInterval(interval);
        }
    }

    public void reconnect() {
        if (isCloseGracefully) {
            if (mReconnectTimerTask != null) {
                mReconnectTimerTask.cancel();
            }
            return;
        }
        if (mWebSocket != null && !mWebSocket.isOpen() && getConnectStatus() != ConnectStatus.CONNECTING) {
            mReconnectTimerTask = new TimerTask() {
                @Override
                public void run() {
                    if (!isCloseGracefully) {
                        connect();
                    }

                }
            };
            mReconnectTimer.schedule(mReconnectTimerTask, DEFAULT_SOCKET_RECONNECTINTERVAL);
        }
    }

    public enum ConnectStatus {
        CONNECT_DISCONNECT,// 断开连接
        CONNECT_SUCCESS,//连接成功
        CONNECT_FAIL,//连接失败
        CONNECTING//正在连接
    }

    public interface WebSocketListener {
        void onConnectError(WebSocketException exception);

        void onDisconnected();

        void onConnected(WebSocket websocket, Map<String, List<String>> headers) throws Exception;

        void onTextMessage(WebSocket websocket, String text) throws Exception;
    }

    class NVWebSocketListener extends WebSocketAdapter {

        @Override
        public void onConnected(WebSocket websocket, Map<String, List<String>> headers) throws Exception {
            super.onConnected(websocket, headers);
            LogUtils.i(TAG, "WebSocket连接成功");
            setConnectStatus(ConnectStatus.CONNECT_SUCCESS);
            if (mWebSocketListener != null) {
                mWebSocketListener.onConnected(websocket, headers);
            }
        }

        @Override
        public void onConnectError(WebSocket websocket, WebSocketException exception) throws Exception {
            super.onConnectError(websocket, exception);
            LogUtils.i(TAG, "WebSocket连接失败");
            setConnectStatus(ConnectStatus.CONNECT_FAIL);
            if (mWebSocketListener != null) {
                mWebSocketListener.onConnectError(exception);
            }
            reconnect();
        }

        @Override
        public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer) throws Exception {
            super.onDisconnected(websocket, serverCloseFrame, clientCloseFrame, closedByServer);
            LogUtils.i(TAG, "WebSocket断开连接");
            printStackTrace();
            setConnectStatus(ConnectStatus.CONNECT_DISCONNECT);
            reconnect();
        }

        @Override
        public void onTextMessage(WebSocket websocket, String text) throws Exception {
            super.onTextMessage(websocket, text);
            if (mWebSocketListener != null) {
                mWebSocketListener.onTextMessage(websocket, text);
            }
        }
    }
}
