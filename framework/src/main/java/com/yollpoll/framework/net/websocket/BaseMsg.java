package com.yollpoll.framework.net.websocket;

import android.text.TextUtils;

/**
 * @author huhao on 2020/09/29.
 */
public class BaseMsg {
    private MsgHeader header;

    public String getMsgId() {
        if (header == null) {
            header = new MsgHeader();
        }
        return header.getMsgId();
    }

    public void setMsgUUId(String msgUUId) {
        if (header == null) {
            header = new MsgHeader();
        }
        header.setSessionId(msgUUId);
    }

    public String getMsgUUId() {
        if (header == null) {
            header = new MsgHeader();
        }
        return header.getSessionId();
    }

    public void setMsgId(String msgId) {
        if (header == null) {
            header = new MsgHeader();
        }
        header.setMsgId(msgId);
    }

    public static class MsgHeader {
        private String msg_id;     // 消息id，ex：Ev_CnApiMsg_UsrLogin_Req
        private String session_id; // 请求时直接传uuid，用于确定消息唯一性；返回时"session_id": "WebSocket:156340968:uuid"，需要解析去除uuid

        public String getMsgId() {
            return msg_id;
        }

        public void setMsgId(String msgId) {
            this.msg_id = msgId;
        }

        public String getSessionId() {
            String msgUUId = "";
            if (!TextUtils.isEmpty(session_id)) {
                String[] str = session_id.split(":");
                if (str.length > 1) {
                    msgUUId = str[str.length - 1];
                }
            }
            return msgUUId;
        }

        public void setSessionId(String sessionId) {
            this.session_id = sessionId;
        }
    }
}