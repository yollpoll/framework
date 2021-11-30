package com.yollpoll.arch.threadpool;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by spq on 2021/11/30
 */
public class   ThreadPool {
    public static final String THREAD_GROUP_NAME = "yollpoll";
    //CPU核心数
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors() + 1;

    //线程池核心线程数量
    private static final int CORE_POOL_SIZE = CPU_COUNT;

    //缓存队列的大小(最大线程数量)
    private static final int MAX_POOL_SIZE = 2 * CORE_POOL_SIZE + 1;

    //非核心线程等待时间，超时回收
    private static final long KEEP_ALIVE_TIME = 10L;

    private static final MyThreadFactory sThreadFactory = new MyThreadFactory("worker");

    private static final ThreadPoolExecutor THREAD_POOL_EXECUTOR = getThreadPoolExecutor();

    public static ThreadPoolExecutor getThreadPoolExecutor() {
        return new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque(128),
                sThreadFactory);
    }

    public static void execute(Runnable command) {
        THREAD_POOL_EXECUTOR.execute(command);
    }

    /**
     * 关闭线程池，这里要说明的是：调用关闭线程池方法后，会移出正在等待的任务，线程池立即退出
     */
    public static void shutdownNow() {
        if (!THREAD_POOL_EXECUTOR.isShutdown())
            THREAD_POOL_EXECUTOR.shutdownNow();
    }

    /**
     * 关闭线程池，这里要说明的是：调用关闭线程池方法后，线程池会执行完队列中的所有任务才退出
     */
    public static void shutdown() {
        THREAD_POOL_EXECUTOR.shutdown();
    }

    /**
     * 提交任务到线程池，可以接收线程返回值
     *
     * @param task
     * @return
     */
    Future<?> submit(Runnable task) {
        return THREAD_POOL_EXECUTOR.submit(task);
    }

    /**
     * 提交任务到线程池，可以接收线程返回值
     * future.get()阻塞线程获取数据
     * @param task
     * @return
     */
    Future<?> submit(Callable<?> task) {
        return THREAD_POOL_EXECUTOR.submit(task);
    }


    //自定义线程
    static class MyThreadFactory implements ThreadFactory {
        private String threadName;

        public MyThreadFactory(String threadName) {
            this.threadName = threadName;
            init();
        }

        private ThreadGroup group;
        private AtomicInteger threadNumber = new AtomicInteger(1);
        private String namePrefix;

        private void init() {
            SecurityManager s = System.getSecurityManager();
            group = s != null ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            namePrefix = THREAD_GROUP_NAME + "-pool-thread-";
        }


        public Thread newThread(Runnable runnable) {
            Thread t = new Thread(group, runnable, namePrefix + threadName + threadNumber.getAndIncrement(), 0);
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }

    }
}
