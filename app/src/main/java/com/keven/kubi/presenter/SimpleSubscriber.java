package com.keven.kubi.presenter;

import rx.Subscriber;

/**
 * Created by keven-liang on 2018/5/12.
 */

public abstract class SimpleSubscriber<T> extends Subscriber<T> {
    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public abstract void onNext(T t) ;
}
