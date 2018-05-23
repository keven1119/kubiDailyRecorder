package com.keven.kubi.presenter;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class RxPresenter  {

    CompositeSubscription compositeSubscription;

    public RxPresenter() {
        compositeSubscription = new CompositeSubscription();
    }

    protected void addTask(Subscription subscription) {
        compositeSubscription.add(subscription);
    }

    public void unsubscribe() {
        compositeSubscription.unsubscribe();
        compositeSubscription.clear();
    }
}