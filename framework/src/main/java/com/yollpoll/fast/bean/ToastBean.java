package com.yollpoll.fast.bean;

import android.widget.Toast;

/**
 * Created by spq on 2021/2/13
 */
public class ToastBean {
    private String message;
    private Duration duration = Duration.SHORT;

    public ToastBean(String message, Duration duration) {
        this.message = message;
        this.duration = duration;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public enum Duration {
        SHORT, LONG
    }
}
