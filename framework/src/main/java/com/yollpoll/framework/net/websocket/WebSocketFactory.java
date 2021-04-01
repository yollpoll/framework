package com.yollpoll.framework.net.websocket;

import android.content.Intent;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.yollpoll.framework.base.BaseApplication;
import com.yollpoll.framework.net.websocket.DisposeService.NtfDisposeService;
import com.yollpoll.framework.net.websocket.msgstrategy.MsgStrategyManager;

/**
 * Created by spq on 2021/2/23
 */
public class WebSocketFactory {
    private final static WebSocketRetrofit webSocketRetrofit;
    private static Intent ntfDisposeService;

    static {
        WebSocketClientComponent webSocketClientComponent = WebSocketClientManager.getInstance().getIntelWebSocketClientComponent();
        webSocketClientComponent.setOnReceiveNotifyListener((msgId, msg) -> {
            if (TextUtils.isEmpty(msgId))
                return;
            //消息交给策略模式处理
            MsgStrategyManager.handle(msgId, msg);
            //把消息交给service处理
            if (null != ntfDisposeService) {
                ntfDisposeService.putExtra("message", msg);
                ntfDisposeService.putExtra("msgId", msgId);
                BaseApplication.getINSTANCE().startService(ntfDisposeService);
            }
        });

        webSocketRetrofit = new WebSocketRetrofit(webSocketClientComponent);
        ntfDisposeService = new Intent(BaseApplication.getINSTANCE(), NtfDisposeService.class);
    }

    public static <T> T createService(@NonNull final Class<T> clazz) {
        return webSocketRetrofit.create(clazz);
    }
}
