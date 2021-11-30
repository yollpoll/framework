package com.yollpoll.arch.util;

import android.widget.Toast;

import com.yollpoll.arch.base.BaseApplication;


/**
 * Created by spq on 2021/2/13
 */
public class ToastUtil {
    public static void showShortToast(String msg){
        Toast.makeText(BaseApplication.getINSTANCE(),msg,Toast.LENGTH_SHORT).show();
    }
    public static void showLongToast(String msg){
        Toast.makeText(BaseApplication.getINSTANCE(),msg,Toast.LENGTH_LONG).show();
    }
}
