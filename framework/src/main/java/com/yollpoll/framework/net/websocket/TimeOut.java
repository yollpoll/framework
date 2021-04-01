package com.yollpoll.framework.net.websocket;

import android.util.Log;


import com.yollpoll.framework.log.LogUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class TimeOut {

    private static int DEFAULT_TIMEOUT_SECS = 60;
    private static ScheduledFuture scheduledFuture;
    private static ScheduledExecutorService service;
    private static List<WebSocketClientComponent> clientComponents = new ArrayList<>(2);
    private static Runnable checkTimeOutTask = () -> {
        try {
            for (WebSocketClientComponent clientComponent : clientComponents) {
                for (RequestMessage msg : clientComponent.getRequestDispatch().cacheQueue) {
                    if (isTimeOut(msg)) {
                        clientComponent.getResponseDispatch().onTimeOut(msg);
                    }
                }
                for (RequestMessage msg : clientComponent.getRequestDispatch().processingMsgList) {
                    if (isTimeOut(msg)) {
                        clientComponent.getResponseDispatch().onTimeOut(msg);
                        if (msg.call == null) {
                            clientComponent.getRequestDispatch().finishRequest(msg.getMsgUUId(), null);
                        }
                    }
                }
            }
        } catch (Exception e) {
            LogUtils.d("checkTimeOutTask", Log.getStackTraceString(e));
        }
    };

    protected static void startCheckTimeOut() {
        if (scheduledFuture == null) {
            synchronized (TimeOut.class) {
                if (scheduledFuture == null) {
                    service = Executors.newScheduledThreadPool(1, new WSThreadFactory("TimeOut"));
                    scheduledFuture = service.scheduleWithFixedDelay(checkTimeOutTask, 0, DEFAULT_TIMEOUT_SECS, TimeUnit.SECONDS);
                }
            }
        } else if (scheduledFuture.isCancelled()) {
            scheduledFuture.notify();
        }
    }

    public static void stopCheckTimeOut() {
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
            service.shutdownNow();
            scheduledFuture = null;
            service = null;
        }
    }

    public static void addClientComponent(WebSocketClientComponent webSocketClientComponent) {
        clientComponents.add(webSocketClientComponent);
    }

    public static boolean isTimeOut(RequestMessage msg) {
        long createTimeStamp = msg.createTimeStamp;
        return System.currentTimeMillis() - createTimeStamp >= DEFAULT_TIMEOUT_SECS * 1000;
    }
}