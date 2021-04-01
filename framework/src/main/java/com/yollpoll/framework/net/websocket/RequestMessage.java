package com.yollpoll.framework.net.websocket;

/**
 * Created by wangqian on 2019/1/17.
 */

public class RequestMessage {

    long createTimeStamp = System.currentTimeMillis();
    String realMsg;
    Call call;
    String msgUUId;

    public RequestMessage(String msgUUId) {
        this.msgUUId = msgUUId;
    }

    public RequestMessage(String msgUUId, Call call) {
        this.msgUUId = msgUUId;
        this.call = call;
    }

    public RequestMessage(String msg, String msgUUId, Call call) {
        this.realMsg = msg;
        this.msgUUId = msgUUId;
        this.call = call;
    }

    public long getCreateTimeStamp() {
        return createTimeStamp;
    }

    public void setCreateTimeStamp(long createTimeStamp) {
        this.createTimeStamp = createTimeStamp;
    }

    public String getRealMsg() {
        return realMsg;
    }

    public void setRealMsg(String realMsg) {
        this.realMsg = realMsg;
    }

    public Call getCall() {
        return call;
    }

    public void setCall(Call call) {
        this.call = call;
    }

    public String getMsgUUId() {
        return msgUUId;
    }

    public void setMsgUUId(String msgId) {
        this.msgUUId = msgId;
    }

    @Override
    public int hashCode() {
        return msgUUId.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestMessage that = (RequestMessage) o;
        return msgUUId.equals(that.msgUUId);
    }
}