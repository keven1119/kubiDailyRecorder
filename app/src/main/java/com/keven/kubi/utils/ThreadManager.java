package com.keven.kubi.utils;

import com.keven.kubi.BuildConfig;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by XMR on 16/4/14.
 */
public class ThreadManager {
    private static ThreadManager instance = null;
    private ExecutorService executorService;
    private JoyrunExecutorService joyrunExecutorService;
    private Executor singleThreadExecutor;
//    private ThreadFactory mThreadFactory;

    private ThreadManager() {
//        mThreadFactory = new MyThreadFactory();
        // 使用自动伸展的线程池
//        executorService = Executors.newCachedThreadPool();
        int threadCount = Runtime.getRuntime().availableProcessors();
        executorService = Executors.newFixedThreadPool(threadCount * 2 + 1);
        joyrunExecutorService = new JoyrunExecutorService(Executors.newSingleThreadExecutor());
        singleThreadExecutor = Executors.newSingleThreadExecutor();
    }

    //    public class MyThreadFactory implements ThreadFactory {
//        private volatile int count;
//
//        @Override
//        public Thread newThread(Runnable r) {
//            String name = "Joyrun-Thread-" + (count++);
//            LogUtils.e("线程池创建线程",name);
//            Thread t = new Thread(r, name);
//            Thread.setDaemon(true);
//            return t;
//        }
//    }
//
    public static ThreadManager getInstance() {
        if (instance == null)
            synchronized (ThreadManager.class) {
                if (instance == null) {
                    instance = new ThreadManager();
                }
            }
        return instance;
    }

    public ExecutorService newExecutorService() {
        return executorService;
    }

    public JoyrunExecutorService newJoyrunExecutorService() {
        return joyrunExecutorService;
    }

    public Executor newSingleThreadExecutor() {
        return singleThreadExecutor;
    }

    public void execute(Runnable runnable) {
        try {
            executorService.execute(new SafeRunnable(runnable));
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static class SafeRunnable implements Runnable {
        private Runnable runnable;

        public SafeRunnable(Runnable runnable) {
            this.runnable = runnable;
        }

        @Override
        public void run() {
            if (BuildConfig.DEBUG) {
                this.runnable.run();
            } else {
                try {
                    this.runnable.run();
                } catch (Throwable e) {
                    RxJavaPluginUtils.handleException(e);
                }
            }
        }
    }
}
