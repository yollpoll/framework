package com.yollpoll.myframework.ui.aidldemo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.yollpoll.myframework.IMyAidlInterface;
import com.yollpoll.myframework.IOnNewMsgListener;
import com.yollpoll.myframework.R;

/**
 * Created by spq on 2020-05-25
 */
public class AIDLTestActivity extends Activity {
    private static final String TAG = "AIDLTestActivity";

    private IMyAidlInterface mMyAidlInterface;
    public static void gotoAIDLTestActivity(Context context){
        Intent intent=new Intent(context,AIDLTestActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        Intent intent=new Intent(this, ADILTestService.class);
        bindService(intent,mConnection, Context.BIND_AUTO_CREATE);
    }
    @Override
    protected void onDestroy() {
        //解除注册
        if(mMyAidlInterface!=null&&
                mMyAidlInterface.asBinder().isBinderAlive()){
            try {
                mMyAidlInterface.unRegisterListener(onNewMsgListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        unbindService(mConnection);
        super.onDestroy();
    }
    private IOnNewMsgListener onNewMsgListener=new IOnNewMsgListener.Stub() {
        @Override
        public void onMsg(String msg) throws RemoteException {
            Log.i(TAG,"收到消息: "+msg);

        }
    };
    private ServiceConnection mConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMyAidlInterface=IMyAidlInterface.Stub.asInterface(service);
            try {
                String myName=mMyAidlInterface.getName();
                Toast.makeText(AIDLTestActivity.this,myName,Toast.LENGTH_SHORT).show();
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            try {
                mMyAidlInterface.registerListener(onNewMsgListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mMyAidlInterface=null;
        }
    };
}
