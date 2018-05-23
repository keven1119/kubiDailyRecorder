package com.keven.kubi.presenter;

import com.keven.kubi.MainApp;
import com.keven.kubi.activity.view.WriteStatisView;
import com.keven.kubi.bean.DailyProductBean;
import com.keven.kubi.bean.FoodHourBean;
import com.keven.kubi.bean.FoodProductionBean;
import com.keven.kubi.dao.DailyProductDao;
import com.keven.kubi.dao.FoodProductionDao;
import com.keven.kubi.dao.FoodSaleDao;
import com.keven.kubi.utils.ThreadManager;
import com.keven.kubi.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by keven-liang on 2018/4/30.
 */

public class WriteStatisPresenter extends RxPresenter {

    private WriteStatisView mWriteStatisView;
    private DailyProductDao mDailyProductDao;
    private FoodSaleDao mFoodSaleDao;

    public WriteStatisPresenter(WriteStatisView writeStatisView){
        mWriteStatisView = writeStatisView;
        mDailyProductDao = new DailyProductDao();
        mFoodSaleDao = new FoodSaleDao();
    }

    public void getFoodHourSaleMsg(){
        addTask(
        Observable.just(1)
                .subscribeOn(Schedulers.from(ThreadManager.getInstance().newExecutorService()))
                .observeOn(Schedulers.from(ThreadManager.getInstance().newExecutorService()))
                .map(new Func1<Integer, List<FoodHourBean>>() {
                    @Override
                    public List<FoodHourBean> call(Integer integer) {

                        long thisHourMillSceond = TimeUtils.getThisHourMillSceond();

                        long currentTimeMillis = System.currentTimeMillis();

                        //统计的时间
                        long statisStartTime = 0;
                        long statisEndTime = 0;

                        if(currentTimeMillis < thisHourMillSceond + 1800*1000){
                            //在统计时间之后的半小时内，应该继续统计上一个小时售卖量
                            statisStartTime = TimeUtils.getPervoiusHourMillSceond();
                            statisEndTime = thisHourMillSceond;
                        }else {
                            //在统计时间之后的半小时后，可以开始统计这个小时内的售卖量
                            statisStartTime = thisHourMillSceond;
                            statisEndTime = TimeUtils.getNextHourMillSecond();
                        }

                        List<FoodProductionBean> allFoodProduction = new FoodProductionDao().getAllFoodProduction();

                        for(FoodProductionBean foodProduction : allFoodProduction){

                            FoodHourBean foodHourBean = mFoodSaleDao.queryHourSale(foodProduction.getFid(), statisStartTime);
                            if(foodHourBean == null){

                                foodHourBean = new FoodHourBean();
                                foodHourBean.setFid(foodProduction.getFid());
                                foodHourBean.setName(foodProduction.getName());
                                foodHourBean.setFoodProductionBean(foodProduction);
                                foodHourBean.setStartTimeStamp(statisStartTime);
                                foodHourBean.setEndTimeStamp(statisEndTime);
                                foodHourBean.setPredictWriteTimeStamp(thisHourMillSceond);
                                mFoodSaleDao.insertOrUpdateHourSale(foodHourBean);
                            }
                        }


                        List<FoodHourBean> foodHourBeanList = mFoodSaleDao.queryAllProductionSaleCount(statisStartTime, statisEndTime);

                        return foodHourBeanList;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleSubscriber<List<FoodHourBean>>() {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mWriteStatisView.onLoadAllFoodTypeFail();
                    }

                    @Override
                    public void onNext(List<FoodHourBean> foodHourBeans) {
                        mWriteStatisView.onLoadAllFoodType(foodHourBeans);
                    }
                })
        );


    }




    public void saveHourSaleData(FoodHourBean foodHourBean, final int inputAmount){
        addTask(
        Observable.just(foodHourBean)
                .subscribeOn(Schedulers.from(ThreadManager.getInstance().newExecutorService()))
                .observeOn(Schedulers.from(ThreadManager.getInstance().newExecutorService()))
                .map(new Func1<FoodHourBean, FoodHourBean>() {
                    @Override
                    public FoodHourBean call(FoodHourBean foodHourBean) {
                        long startTime = TimeUtils.getTodayMillSceond();
                        long endTime = startTime + 24 * 3600 * 1000;

                        List<FoodHourBean> foodHourBeanList = mFoodSaleDao.queryProductionSaleCount(foodHourBean.getFid(), startTime, endTime);
                        DailyProductBean dailyProductBean = mDailyProductDao.queryDailyProductByFid(foodHourBean.getFid(), startTime);

                        int saleCount = 0;
                        if(foodHourBeanList == null || foodHourBeanList.size() < 1){
                            //今天没有统计过销量
                             saleCount = dailyProductBean.getProductCount() - foodHourBean.getResidueCount();

                        }else {
                            Integer productCount = dailyProductBean.getProductCount();
                            //生产总量减去所有销量就是上小时的销量
                            for (FoodHourBean hourBean: foodHourBeanList){
                                if(hourBean.getFid() == foodHourBean.getFid() && hourBean.getStartTimeStamp() == foodHourBean.getStartTimeStamp()){
                                    continue;
                                }
                                productCount = productCount - hourBean.getHourScaleCount();
                            }
                            saleCount = productCount - foodHourBean.getResidueCount()-inputAmount;
                        }

                        if(saleCount < 0){
                            RuntimeException runtimeException = new RuntimeException("产品的销量大于产品的生产\n请注意检查");
                            throw runtimeException;

                        }else {

                            int residueCount = foodHourBean.getResidueCount();
                            foodHourBean.setResidueCount(residueCount + inputAmount);
                            foodHourBean.setHourScaleCount(saleCount);
                            foodHourBean.setRealityWriteTimeStamp(System.currentTimeMillis());

                            mFoodSaleDao.insertOrUpdateHourSale(foodHourBean);
                        }

                        return foodHourBean;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleSubscriber<FoodHourBean>() {
                    @Override
                    public void onNext(FoodHourBean foodHourBean) {
                        mWriteStatisView.onSaveHourDataSuc(foodHourBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mWriteStatisView.onSaveHourDataFail(e);

                    }
                })
        );


    }

}
