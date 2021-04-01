package com.yollpoll.framework.net.websocket.DisposeService;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.yollpoll.framework.net.websocket.msgstrategy.MsgStrategyManager;


/**
 * Created by spq on 2020/12/4
 */
public class NtfDisposeService extends IntentService {
    private static final String TAG = "NtfDisposeService";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public NtfDisposeService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String msgId = intent.getStringExtra("msgId");
        String message = intent.getStringExtra("message");
        MsgStrategyManager.handle(msgId,message);
    }
}
