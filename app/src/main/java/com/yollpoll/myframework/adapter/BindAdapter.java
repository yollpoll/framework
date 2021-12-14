package com.yollpoll.myframework.adapter;

import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.databinding.BindingAdapter;
import androidx.databinding.BindingConversion;


/**
 * Created by spq on 2020-06-04
 * databinding 自定义bindingAdapter
 */
public class BindAdapter {
    private static final String TAG = "BindAdapter";

    @BindingAdapter("setText2")
    public static void setText2(TextView view, String content){
        Log.d(TAG, "setPadding: "+content);
        view.setText(content);
    }


    /**
     * 转化器，当需要drawable时候自动将int转化为drawable
     * @param color
     * @return
     */
    @BindingConversion
    public static ColorDrawable convertColorToDrawable(int color) {
        Log.d(TAG, "convertColorToDrawable: "+color);
        return new ColorDrawable(color);
    }
}
