package com.keven.kubi.presenter;

import com.keven.kubi.MainApp;
import com.keven.kubi.activity.view.AddFoodView;
import com.keven.kubi.bean.FoodProductionBean;
import com.keven.kubi.dao.FoodProductionDao;
import com.keven.kubi.utils.ThreadManager;
import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by keven-liang on 2018/4/30.
 */

public class AddFoodPresenter extends RxPresenter {

    private AddFoodView mAddFoodView;

    public AddFoodPresenter(AddFoodView addFoodView){
        mAddFoodView = addFoodView;
    }

    public void addNewFood( FoodProductionBean foodProduction){
        addTask(
                Observable.just(foodProduction)
                    .subscribeOn(Schedulers.from(ThreadManager.getInstance().newExecutorService()))
                    .observeOn(Schedulers.from(ThreadManager.getInstance().newExecutorService()))
                    .map(new Func1<FoodProductionBean, FoodProductionBean>() {
                        @Override
                        public FoodProductionBean call(FoodProductionBean food) {

                            FoodProductionDao foodProductionDao = new FoodProductionDao();
                            FoodProductionBean unique = foodProductionDao.queryFoodByName(food.getName());

                            if(unique != null ){
                                foodProductionDao.delete(food);
                            }

                            foodProductionDao.insert(food);

                            return food;
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SimpleSubscriber<FoodProductionBean>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            mAddFoodView.onAddedFailed();
                        }

                        @Override
                        public void onNext(FoodProductionBean foodProductionBean) {
                            mAddFoodView.onAddedSuccess();
                        }
                    })

        );

    }

    public void getAllFoodFromDB(){
        addTask(
        Observable.just(1)
                .subscribeOn(Schedulers.from(ThreadManager.getInstance().newExecutorService()))
                .observeOn(Schedulers.from(ThreadManager.getInstance().newExecutorService()))
                .map(new Func1<Integer, List<FoodProductionBean>>() {
                    @Override
                    public List<FoodProductionBean> call(Integer integer) {
                        List<FoodProductionBean> foodProductionBeans = new FoodProductionDao().getAllFoodProduction();
                        return foodProductionBeans;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleSubscriber<List<FoodProductionBean>>() {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mAddFoodView.onGetAllFoodFromDBFail();
                    }

                    @Override
                    public void onNext(List<FoodProductionBean> foodProductionBeans) {
                        mAddFoodView.onGetAllFoodFromDB(foodProductionBeans);
                    }
                })
        );
    }


    public void updateFoodMsg(FoodProductionBean foodProduction){
        addTask(
        Observable.just(foodProduction)
                .subscribeOn(Schedulers.from(ThreadManager.getInstance().newJoyrunExecutorService()))
                .observeOn(Schedulers.from(ThreadManager.getInstance().newJoyrunExecutorService()))
                .map(new Func1<FoodProductionBean, FoodProductionBean>() {
                    @Override
                    public FoodProductionBean call(FoodProductionBean foodProductionBean) {
                        new FoodProductionDao().update(foodProductionBean);
                        return foodProductionBean;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleSubscriber<FoodProductionBean>() {
                    @Override
                    public void onNext(FoodProductionBean foodProductionBean) {
                        mAddFoodView.onUpdateFoodSuccess(foodProductionBean);
                    }
                })
        );
    }

    @Override
    public void unsubscribe() {
        super.unsubscribe();
        mAddFoodView = null;
    }
}
