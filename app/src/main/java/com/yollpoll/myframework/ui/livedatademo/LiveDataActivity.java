package com.yollpoll.myframework.ui.livedatademo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.yollpoll.framework.base.BaseActivity;
import com.yollpoll.myframework.R;

/**
 * Created by spq on 2020-06-11
 */
public class LiveDataActivity extends BaseActivity {
    private LiveDataViewModule viewModule;
    private TextView tvContent;

    public static void gotoLiveDataActivity(Context context){
        Intent intent=new Intent(context,LiveDataActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livedata);
        viewModule= new ViewModelProvider(this).get(LiveDataViewModule.class);
        initView();
        initData();
    }
    private void initData(){
        //this 是lifecycleOwner
        viewModule.getContent().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tvContent.setText(s);
            }
        });
    }
    private void initView(){
        tvContent=findViewById(R.id.tv_content);
    }
    public void changeValue(View view){
        viewModule.getContent().setValue("手动修改信息");
    }
}
