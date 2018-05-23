package com.keven.kubi.utils;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class JoyrunExecutorService implements ExecutorService {

    private ExecutorService mExecutorService;
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(3);

    public JoyrunExecutorService(ExecutorService executorService) {
        this.mExecutorService = executorService;
    }

    /**
     * @param task
     * @param delay MILLISECONDS
     */
    public void execute(final Runnable task, final long delay) {
        scheduler.schedule(new Runnable() {

            @Override
            public void run() {
                mExecutorService.execute(task);
            }
        }, delay, TimeUnit.MILLISECONDS);
    }

    @Override
    public void execute(final Runnable task) {
        mExecutorService.execute(task);
    }

    @Override
    public void shutdown() {
        mExecutorService.shutdown();
    }

    @Override
    public List<Runnable> shutdownNow() {
        return mExecutorService.shutdownNow();
    }

    @Override
    public boolean isShutdown() {
        return mExecutorService.isShutdown();
    }

    @Override
    public boolean isTerminated() {
        return mExecutorService.isTerminated();
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return mExecutorService.awaitTermination(timeout, unit);
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return mExecutorService.submit(task);
    }

    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        return mExecutorService.submit(task, result);
    }

    @Override
    public Future<?> submit(Runnable task) {
        return mExecutorService.submit(task);
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
        return mExecutorService.invokeAll(tasks);
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
            throws InterruptedException {
        return mExecutorService.invokeAll(tasks, timeout, unit);
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
        return mExecutorService.invokeAny(tasks);
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException,
            ExecutionException, TimeoutException {
        return mExecutorService.invokeAny(tasks, timeout, unit);
    }

}