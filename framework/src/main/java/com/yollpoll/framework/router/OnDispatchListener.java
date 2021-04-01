package com.yollpoll.framework.router;

import android.content.Context;

import java.util.HashMap;

public interface OnDispatchListener {
    void onDispatch(HashMap<String, String> params, Context context, OnBackListener onBackListener);
}
