package com.yollpoll.myframework.ui.aidldemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

import com.yollpoll.framework.log.LogUtils;
import com.yollpoll.myframework.IMyAidlInterface;
import com.yollpoll.myframework.IOnNewMsgListener;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by spq on 2020-05-21
 */
public class ADILTestService extends Service {
    private static final String TAG = "ADILTestService";
    private AtomicBoolean isDestory=new AtomicBoolean(false);
    //这种数据类型是专门用来解除注册的，因为在传递中对象是不会传递的而是序列化以后传递，所以需要用这种类型去找到注册过的真正的对象，
    //同时这种类型具有线程安全
    private RemoteCallbackList<IOnNewMsgListener> mCallbacks=new RemoteCallbackList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        //每5s给所有连接上service的客户端发送消息
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isDestory.get()){
                    try {
                        Thread.sleep(5000);
                        onNewMsg("My name is yollpoll/"+System.currentTimeMillis());
                    } catch (InterruptedException | RemoteException e) {
                        e.printStackTrace();
                        LogUtils.d("error",e);
                    }

                }
            }
        }).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //线程退出标志
        isDestory.set(true);
    }

    private void onNewMsg(String msg) throws RemoteException {
        final  int N=mCallbacks.beginBroadcast();
        for(int i=0;i<N;i++){
            IOnNewMsgListener onNewMsgListener= (IOnNewMsgListener) mCallbacks.getBroadcastCookie(i);
            if(null!=onNewMsgListener){
                onNewMsgListener.onMsg(msg);
            }
        }
        mCallbacks.finishBroadcast();
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    public class MyBinder extends IMyAidlInterface.Stub {


        @Override
        public String getName() throws RemoteException {
            return "连接service成功 / "+System.currentTimeMillis();
        }

        //注册回调
        @Override
        public void registerListener(IOnNewMsgListener listener) throws RemoteException {
            Log.d(TAG,"register");
            mCallbacks.register(listener);

        }

        @Override
        public void unRegisterListener(IOnNewMsgListener listener) throws RemoteException {
            Log.d(TAG,"unRegister");
            mCallbacks.unregister(listener);

        }


    }


}
