package com.yollpoll.arch.message.liveeventbus;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.yollpoll.arch.threadpool.ThreadPool;

import java.util.concurrent.TimeUnit;

/**
 * Created by spq on 2021/2/14
 * 和lifecycle绑定的事件总线
 * 每添加一个observer，LiveEventWrapper 的序列号增加1，并赋值给新加的observer，
 * 每次消息更新使用目前的序列号进行请求，持有更小的序列号才需要获取变更通知。
 * <p>
 * 解决会收到注册前发送的消息更新问题
 */
public class LiveDataWrapper<T> implements Observable<T> {
    private static final String TAG = "LiveEventWrapper";
    private int uid = 0;
    private final MutableLiveData<EventWrapper<T>> mutableLiveData;
    private String key;

    public LiveDataWrapper(String key) {
        this.key = key;
        mutableLiveData = new MutableLiveData<>();
    }

    /**
     * 如果在多线程中调用，保留每一个值
     * 无需关心调用线程，只要确保在相同进程中就可以
     *
     * @param value 需要更新的值
     */
    public void post(@NonNull T value) {
        checkThread(() -> setValue(value));
    }

//    /**
//     * 设置监听之前发送的消息不可以接收到
//     * 重写 observer 的函数 isSticky ，返回true，可以实现粘性事件
//     *
//     * @param owner           生命周期拥有者
//     * @param observerWrapper 观察者包装类
//     */
//    public void observe(@NonNull LifecycleOwner owner, @NonNull ObserverWrapper<T> observerWrapper) {
//        observerWrapper.setUid(observerWrapper.isSticky() ? -1 : uid++);
//        checkThread(() -> mutableLiveData.observe(owner, filterObserver(observerWrapper)));
//    }

    /**
     * 检测是否当前线程是主线程
     *
     * @return
     */
    private boolean isMainThread() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }

    /**
     * 检查线程并切换到主线程
     */
    private void checkThread(Runnable runnable) {
        if (isMainThread()) {
            runnable.run();
        } else {
            getMainHandler().post(runnable);
        }
    }

    /**
     * 获取主线程handler
     *
     * @return
     */
    private Handler getMainHandler() {
        return new Handler(Looper.getMainLooper());
    }

    /**
     * 设置数据,增加序号
     *
     * @param value
     */
    private void setValue(T value) {
        EventWrapper<T> eventWrapper = new EventWrapper<>(uid, value);
        uid++;
        mutableLiveData.setValue(eventWrapper);
    }

    /**
     * 从包装类中过滤出原始观察者
     *
     * @param observerWrapper 包装类
     * @return 原始观察者
     */
    @NonNull
    private Observer<EventWrapper<T>> filterObserver(@NonNull final ObserverWrapper<T> observerWrapper) {
        if (observerWrapper.getObserver() != null) {
            return observerWrapper.getObserver();
        }
        observerWrapper.setObserver(eventWrapper -> {
            // 产生的事件序号要大于观察者序号才被通知事件变化
            if (eventWrapper != null && eventWrapper.getUid() > observerWrapper.getUid()) {
                if (observerWrapper.mainThread()) {
                    //主线程中回调
                    observerWrapper.onChanged(eventWrapper.getValue());
                } else {
                    //切换到子线程回调
                    ThreadPool.execute(() -> observerWrapper.onChanged(eventWrapper.getValue()));
                }
            }
        });
        return observerWrapper.getObserver();
    }


    /****************************Observable****************************/

    @Override
    public void postValueDelay(T value, long delay) {
        getMainHandler().postDelayed(() -> setValue(value), delay);
    }

    @Override
    public void postValueDelay(T value, long delay, TimeUnit timeUnit) {
        getMainHandler().postDelayed(() -> setValue(value), TimeUnit.MILLISECONDS.convert(delay, timeUnit));
    }

    /**
     * 设置监听之前发送的消息不可以接收到
     * 重写 observer 的函数 isSticky ，返回true，可以实现粘性事件
     *
     * @param owner           生命周期拥有者
     * @param observerWrapper 观察者包装类
     */
    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull ObserverWrapper<T> observerWrapper) {
        observerWrapper.setUid(observerWrapper.isSticky() ? -1 : uid++);
        checkThread(() -> mutableLiveData.observe(owner, filterObserver(observerWrapper)));
    }

    @Override
    public void observeForever(@NonNull ObserverWrapper<T> observerWrapper) {
        observerWrapper.setUid(observerWrapper.isSticky() ? -1 : uid++);
        checkThread(() -> mutableLiveData.observeForever(filterObserver(observerWrapper)));
    }

    @Override
    public void removeObserver(@NonNull ObserverWrapper<T> observerWrapper) {
        //bus中remove
        LiveEventBus.ready().getBus().remove(key);
        //live自身remove所有观察者
        if (mutableLiveData.hasObservers()) {
            mutableLiveData.removeObserver(observerWrapper.getObserver());
        }
    }

}
