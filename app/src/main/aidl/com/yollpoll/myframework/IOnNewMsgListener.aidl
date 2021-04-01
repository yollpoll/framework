// IOnNewMsgListener.aidl
package com.yollpoll.myframework;

// Declare any non-default types here with import statements
//这个是service中观察者模式里注册的回调
interface IOnNewMsgListener {

    void onMsg(in String msg);
}
