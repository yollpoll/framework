package com.yollpoll.arch.message.liveeventbus;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by spq on 2021/2/14
 * 基于LiveData的eventBus
 */
public  class  LiveEventBus {
    private final Map<String, LiveDataWrapper<Object>> bus;//事件存储map

    public static <T> LiveDataWrapper<T> use(String key, Class<T> clz) {
        return ready().with(key, clz);
    }

    /**
     * 私有构造方法
     */
    private LiveEventBus() {
        bus = new HashMap<>();
    }

    private static class InstanceHolder {
        static final LiveEventBus INSTANCE = new LiveEventBus();
    }

    public static LiveEventBus ready() {
        return InstanceHolder.INSTANCE;
    }

    @SuppressWarnings("unchecked")
    private <T> LiveDataWrapper<T> with(String key, Class<T> clz) {
        if (!bus.containsKey(key)) {
            LiveDataWrapper<Object> liveData = new LiveDataWrapper<>(key);
            bus.put(key, liveData);
        }
        return (LiveDataWrapper<T>) bus.get(key);
    }

    public Map<String, LiveDataWrapper<Object>> getBus() {
        return bus;
    }
}
