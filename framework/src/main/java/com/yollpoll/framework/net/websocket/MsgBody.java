package com.yollpoll.framework.net.websocket;

public class MsgBody<T> {
    private String name;
    private T content;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public void setName() {
        if (null == content)
            return;
        name = "cn." + content.getClass().getSimpleName();
    }
}
