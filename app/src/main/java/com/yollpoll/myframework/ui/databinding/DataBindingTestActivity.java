package com.yollpoll.myframework.ui.databinding;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.yollpoll.framework.base.BaseActivity;
import com.yollpoll.myframework.R;
import com.yollpoll.myframework.databinding.ActivityDatabindingTestBinding;

/**
 * Created by spq on 2020-06-02
 */
public class DataBindingTestActivity extends BaseActivity {
    public static void gotoActivity(Context context){
        Intent intent=new Intent(context,DataBindingTestActivity.class);
        context.startActivity(intent);
    }
    ActivityDatabindingTestBinding activityDatabindingTestBinding;
    DataBindingViewModel viewModule;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDatabindingTestBinding=DataBindingUtil.setContentView(this, R.layout.activity_databinding_test);
        viewModule=new DataBindingViewModel();
        activityDatabindingTestBinding.setBean(viewModule.testDatabindingModel);
    }
}
