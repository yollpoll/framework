package com.yollpoll.myframework;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.yollpoll.annotation.annotation.MethodReference;
import com.yollpoll.annotation.annotation.OnMessage;
import com.yollpoll.fast.FastActivity;
import com.yollpoll.framework.annotation.ContentView;
import com.yollpoll.framework.annotation.PermissionAuto;
import com.yollpoll.framework.annotation.ViewModel;
import com.yollpoll.framework.base.BaseActivity;
import com.yollpoll.framework.log.LogUtils;
import com.yollpoll.framework.utils.ToastUtil;
import com.yollpoll.myframework.databinding.ActivityMainBinding;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

@ContentView(R.layout.activity_main)
@ViewModel(MainViewModel.class)
@PermissionAuto
public class MainActivity extends BusinessActivity<ActivityMainBinding, MainViewModel> {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        Log.d(TAG, "onCreate: " + Thread.currentThread().getName());

    }

    @OnMessage()
    public void onMessage(String message) {
        ToastUtil.showLongToast(message);
    }

    @MethodReference
    public boolean preMethod() {
        LogUtils.i("preMethod");
//        ToastUtil.showLongToast("检测不合格");
        return true;
    }
    @MethodReference
    public void testMR(){

    }
}
