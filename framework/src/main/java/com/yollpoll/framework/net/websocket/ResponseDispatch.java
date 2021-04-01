package com.yollpoll.framework.net.websocket;

import android.os.Handler;
import android.os.Looper;

import com.yollpoll.framework.utils.JsonUtilKt;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by wangqian on 2019/1/16.
 */
public class ResponseDispatch {

    private static final String TAG = "ResponseDispatch";

    private Handler handler = new Handler(Looper.getMainLooper());
    private OnReceiveNotifyListener nOnReceiveNotifyListener;
    private Map<String, RequestMessage> MsgType2Callback = new HashMap<>(); // 缓存60s请求

    public void dispatch(String message) {
        final BaseMsg baseMsg = JsonUtilKt.fromJson(message, BaseMsg.class);
        final RequestMessage requestMessage = getCallByMsgId(baseMsg.getMsgUUId());
        if (requestMessage != null) {
            //本次请求带有onResponse回调
            if (requestMessage.getCall().getResponse() != null) {
                Type resultType = requestMessage.getCall().getReturnType();
                if (resultType != null) {
                    Object object = JsonUtilKt.fromJson(message, resultType);
                    handler.post(() -> requestMessage.getCall().getResponse().onSuccess(object, baseMsg.getMsgId(), message));
                }
            }
            finishRequest(requestMessage);
//            finishRequest();
        } else {
            //没有带有回调，交给监听器处理
            if (nOnReceiveNotifyListener != null) {
                nOnReceiveNotifyListener.onReceiveNotify(baseMsg.getMsgId(), message);
            }
        }
    }

    private RequestMessage getCallByMsgId(String msgUUId) {
        return MsgType2Callback.get(msgUUId);
    }

    /**
     * 结束请求，清除超过缓存时间的message和call的存储
     */
    private void finishRequest() {
        for (Iterator<Map.Entry<String, RequestMessage>> it = MsgType2Callback.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, RequestMessage> item = it.next();
            RequestMessage requestMessage = item.getValue();
            // 每次请求回调后，删除RequestDispatch中的cacheQueue对应call存储，防止报请求超时
            requestMessage.getCall().finishRequest();
            // 此类中的call的存储，固定保存一段时间（默认60s），防止一条请求对应多个回调
            if (TimeOut.isTimeOut(requestMessage)) {
                it.remove();
            }
        }
    }

    /**
     * 请求超时，清除message和call的存储
     */
    private void finishRequest(RequestMessage requestMessage) {
        Call call = requestMessage.getCall();
        // 清除message存储
        call.finishRequest();
        // 清除call的存储
        MsgType2Callback.remove(requestMessage.msgUUId);
    }

    void addCall(String msgUUId, Call call) {
        MsgType2Callback.put(msgUUId, new RequestMessage(msgUUId, call));
    }

    void setOnReceiveNotifyListener(OnReceiveNotifyListener onReceiveNotifyListener) {
        nOnReceiveNotifyListener = onReceiveNotifyListener;
    }

    public void onTimeOut(RequestMessage message) {
        Call call = message.getCall();
        if (call != null) {
            handler.post(() -> {
                if (call.getResponse() != null) {
                    call.getResponse().onTimeOut();
                }
            });
            finishRequest(message);
        }
    }
}