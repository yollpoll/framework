// IMyAidlInterface.aidl
package com.yollpoll.myframework;
import com.yollpoll.myframework.IOnNewMsgListener;

// Declare any non-default types here with import statements

interface IMyAidlInterface {

    String getName();

    void registerListener(IOnNewMsgListener listener);
    void unRegisterListener(IOnNewMsgListener listener);
}
