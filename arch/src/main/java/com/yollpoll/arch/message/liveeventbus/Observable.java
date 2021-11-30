package com.yollpoll.arch.message.liveeventbus;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;

import java.util.concurrent.TimeUnit;

/**
 * Created by spq on 2021/2/23
 */
public interface Observable<T> {
    void post(T value);

    void postValueDelay(T value, long delay);

    void postValueDelay(T value, long delay, TimeUnit timeUnit);

    /**
     * 回调与生命周期相关，在LifecycleOwner处于活动状态才会回调onchange，
     *
     * @param owner
     * @param observer
     */
    void observe(@NonNull LifecycleOwner owner, @NonNull ObserverWrapper<T> observer);

//    /**
//     * 粘性监听，ActivityA setValue,然后跳转到ActivityB,ActivityB调用此方法设置监听，则会回调，
//     * 如果ActivityB destroy,则value会被销毁，然后再跳转到ActivityC,如果C也设置监听，则不会回调
//     * 回调与生命周期相关，在LifecycleOwner处于活动状态才会回调onchange，
//     *
//     * @param owner
//     * @param observer
//     */
//    void observeSticky(@NonNull LifecycleOwner owner, @NonNull ObserverWrapper<T> observer);

//    /**
//     * 建立监听
//     *
//     * @param owner
//     * @param observer
//     * @param always   false-在LifecycleOwner处于活动状态才会回调onchange，true-一直回调
//     */
//    void observe(@NonNull LifecycleOwner owner, @NonNull ObserverWrapper<T> observer, boolean always);
//
//    /**
//     * 建立监听
//     *
//     * @param owner
//     * @param observer
//     * @param always   false-在LifecycleOwner处于活动状态才会回调onchange，true-一直回调
//     * @param discard  true&&always=false在LifecycleOwner处于非活动状态下，发送消息不接收
//     */
//    void observe(@NonNull LifecycleOwner owner, @NonNull ObserverWrapper<T> observer, boolean always, boolean discard);

    /**
     * 与生命周期无关，粘性回调
     *
     * @param observer
     */
    void observeForever(@NonNull ObserverWrapper<T> observer);

//    /**
//     * 粘性监听
//     *
//     * @param owner
//     * @param observer
//     * @param always   true-在任何情况下都响应，false-只在active状态下才响应
//     * @param discard  true-在非活动状态下setvalue，回到active状态也不响应
//     */
//    void observeSticky(@NonNull LifecycleOwner owner, @NonNull ObserverWrapper<T> observer, boolean always, boolean discard);

//    /**
//     * 粘性监听
//     * @param owner
//     * @param observer 只在active状态下响应onChange
//     */
////        void observeSticky(@NonNull LifecycleOwner owner, @NonNull Observer<T> observer);
//
//    /**
//     * 粘性监听
//     *
//     * @param observer
//     */
//    void observeStickyForever(@NonNull ObserverWrapper<T> observer);

    /**
     * 移除observer
     *
     * @param observer
     */
    void removeObserver(@NonNull ObserverWrapper<T> observer);
}
