package com.keven.kubi.presenter;

import com.keven.kubi.activity.view.AddDailyProductView;
import com.keven.kubi.bean.DailyProductBean;
import com.keven.kubi.bean.FoodProductionBean;
import com.keven.kubi.dao.DailyProductDao;
import com.keven.kubi.dao.FoodProductionDao;
import com.keven.kubi.utils.ThreadManager;
import com.keven.kubi.utils.TimeUtils;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by keven-liang on 2018/5/15.
 */

public class AddDailyProductPresenter extends RxPresenter {

    private AddDailyProductView mAddDailyProductView;
    private DailyProductDao mDailyProductDao;
    private FoodProductionDao mFoodProductionDao;


    public AddDailyProductPresenter(AddDailyProductView addDailyProductView){
        mAddDailyProductView = addDailyProductView;
        mDailyProductDao = new DailyProductDao();
        mFoodProductionDao = new FoodProductionDao();
    }

    /**
     * 将所有产品的预添加到产量表
     */
    public void getTodayDailyProduction(){
        addTask(
        Observable.just(1)
                .observeOn(Schedulers.from(ThreadManager.getInstance().newJoyrunExecutorService()))
                .subscribeOn(Schedulers.from(ThreadManager.getInstance().newJoyrunExecutorService()))
                .map(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {

                        List<FoodProductionBean> allFoodProduction = mFoodProductionDao.getAllFoodProduction();
                        long todayMillSceond = TimeUtils.getTodayMillSceond();

                        for (FoodProductionBean foodProductionBean: allFoodProduction){

                            DailyProductBean dailyProductBean = mDailyProductDao.queryDailyProductByFid(foodProductionBean.getFid(), todayMillSceond);
                            if(dailyProductBean == null){

                                dailyProductBean = new DailyProductBean();
                                dailyProductBean.setFid(foodProductionBean.getFid());
                                dailyProductBean.setDateTime(todayMillSceond);
                                dailyProductBean.setName(foodProductionBean.getName());
                                dailyProductBean.setProductCount(0);

                                mDailyProductDao.insertOrUpdataDailyProduct(dailyProductBean);
                            }
                        }

                        return true;
                    }
                })
                .map(new Func1<Boolean, List<DailyProductBean>>() {
                    @Override
                    public List<DailyProductBean> call(Boolean aBoolean) {

                        long todayMillSceond = TimeUtils.getTodayMillSceond();

                        List<DailyProductBean> dailyProductBeans = mDailyProductDao.queryAllDailyProduct(todayMillSceond);

                        return dailyProductBeans;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleSubscriber<List<DailyProductBean>>() {
                    @Override
                    public void onNext(List<DailyProductBean> dailyProductBeans) {
                        mAddDailyProductView.onAllDailyProductFromDB(dailyProductBeans);
                    }
                })
        );


    }


}
