package com.keven.kubi.presenter;

import android.util.Pair;

import com.github.mikephil.charting.data.Entry;
import com.keven.kubi.activity.view.SaleStatisView;
import com.keven.kubi.bean.FoodHourBean;
import com.keven.kubi.bean.FoodProductionBean;
import com.keven.kubi.bean.ProductionDailySaleBean;
import com.keven.kubi.dao.FoodProductionDao;
import com.keven.kubi.dao.FoodSaleDao;
import com.keven.kubi.utils.ThreadManager;
import com.keven.kubi.utils.TimeUtils;

import org.greenrobot.greendao.generator.Entity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by keven-liang on 2018/5/12.
 */

public class SaleStatisPresenter extends RxPresenter {

    private SaleStatisView saleStatisView;
    private FoodSaleDao mFoodSaleDao;

    public SaleStatisPresenter(SaleStatisView saleStatisView){
        this.saleStatisView = saleStatisView;
        mFoodSaleDao = new FoodSaleDao();
    }

    /**
     * 获取 每周某产品的每日的销量数据,一次获取6周
     * @param fid
     * @param count 查询几周
     *
     */
    public void getProductionDailySale(final long fid, final int count){
        addTask(
        Observable.just(fid)
                .subscribeOn(Schedulers.from(ThreadManager.getInstance().newJoyrunExecutorService()))
                .observeOn(Schedulers.from(ThreadManager.getInstance().newJoyrunExecutorService()))
                .map(new Func1<Long, List<Pair<String,List<Entry>>>>() {
                    @Override
                    public List<Pair<String, List<Entry>>> call(Long foodId) {

                        ArrayList<Pair<String, List<Entry>>> weeksSaleList = new ArrayList<>();

                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy年MM月dd日");
                        Date date = new Date();

                        long thisWeekStartMillSceond = TimeUtils.getThisWeekStartMillSceond();
                        for (int i = 0; i < count; i++ ){

                            long weekStartMillSceond = thisWeekStartMillSceond - i * 7 * 24 * 3600 * 1000;

                            long weekEndMillSecond = weekStartMillSceond + 7 *  24 * 3500 * 1000;

                            List<Entry> weekDailySaleList = getWeekDailySaleList(foodId,weekStartMillSceond);

                            date.setTime(weekStartMillSceond);
                            String startDay = simpleDateFormat.format(date);
                            date.setTime(weekEndMillSecond);
                            String endDay = simpleDateFormat.format(date);

                            String dateStr = startDay + "---" + endDay;

                            if(weekDailySaleList != null && weekDailySaleList.size() > 0) {

                                Pair<String, List<Entry>> stringListPair = new Pair<>(dateStr, weekDailySaleList);

                                weeksSaleList.add(stringListPair);
                            }
                        }

                        return weeksSaleList;
                    }


                    private List<Entry> getWeekDailySaleList(long foodId, long weekStartMillSecond){

                        long dayStartMillSecond  = weekStartMillSecond;
                        long currentTimeMillis = System.currentTimeMillis();

                        ArrayList<Entry> productionDailySaleList = new ArrayList<>();

                        int i = 1;
                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy年MM月dd日");
                        Date date = new Date();


                        while (dayStartMillSecond < currentTimeMillis){
                            ProductionDailySaleBean productionDailySaleBean = mFoodSaleDao.queryDaySale(foodId, weekStartMillSecond);

                            date.setTime(dayStartMillSecond);
                            String format = simpleDateFormat.format(date);
                            Entry entry = new Entry(i,productionDailySaleBean.getDayScaleCount(),format);

                            productionDailySaleList.add(entry);

                            dayStartMillSecond  = dayStartMillSecond + 24 * 3600 *1000;
                            i++;

                        }

                        return productionDailySaleList;

                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleSubscriber<List<Pair<String,List<Entry>>>>() {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }

                    @Override
                    public void onNext(List<Pair<String,  List<Entry>>> pairs) {
                        saleStatisView.onWeekSaleDateFromDb(fid, pairs);
                    }
                })
        );
    }


    /**
     * 获取每星期某个产品在某星期的一天的小时销量情况。一次获取6周
     * @param fid
     * @param weekDay  1,2,3,4,5,6,7
     * @param count 查询几周
     */
    public void getProductionHourlySale(
            long fid, final int weekDay, final int count){

        addTask(
        Observable.just(fid)
                .subscribeOn(Schedulers.from(ThreadManager.getInstance().newJoyrunExecutorService()))
                .observeOn(Schedulers.from(ThreadManager.getInstance().newJoyrunExecutorService()))
                .map(new Func1<Long,List<Pair<String,List<Entry>>> >() {

                    @Override
                    public List<Pair<String,List<Entry>>> call(Long foodId) {
                        long thisWeekDayStartTimeStamp = 0;
                        if(weekDay > 0) {
                            thisWeekDayStartTimeStamp = TimeUtils.getTodayWeekDayTimeStamp(weekDay);
                        }else {
                            thisWeekDayStartTimeStamp = TimeUtils.getTodayMillSceond();
                        }

                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy年MM月dd日");
                        Date date = new Date();

                        ArrayList<Pair<String, List<Entry>>> pairs = new ArrayList<>();

                        for (int i = 0; i< count; i++){

                            long weekDayStartTimeStamp = thisWeekDayStartTimeStamp - i * 7 * 24 * 3600 * 1000;

                            ArrayList<Entry> foodHourList = new ArrayList<>();
                            for (int j = TimeUtils.START_HOUR; j<=TimeUtils.END_HOUR; j++) {
                                long hourStartTime = weekDayStartTimeStamp + j * 3600 * 1000;

                                FoodHourBean foodHourBean = mFoodSaleDao.queryHourSale(foodId, hourStartTime);

                                if(foodHourBean != null) {
                                    date.setTime(hourStartTime);
                                    String start = simpleDateFormat.format(date);
                                    date.setTime(hourStartTime + 3600 * 1000);
                                    String end = simpleDateFormat.format(date);

                                    Entry entry = new Entry(j, foodHourBean.getHourScaleCount(), start + "--" + end);
                                    foodHourList.add(entry);
                                }
                            }

                            date.setTime(weekDayStartTimeStamp);
                            String format = simpleDateFormat.format(date);

                            String dateStr = "周" + TimeUtils.getWeekDayByTimeStamp(thisWeekDayStartTimeStamp) + "--（" + format+"）";


                            if(foodHourList != null && foodHourList.size() > 0) {
                                Pair<String, List<Entry>> stringListPair = new Pair<String, List<Entry>>(dateStr, foodHourList);
                                pairs.add(stringListPair);
                            }

                        }

                        return pairs;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleSubscriber<List<Pair<String, List<Entry>>>>() {
                    @Override
                    public void onNext(List<Pair<String, List<Entry>>> pairs) {
                     saleStatisView.onWeekDayHourSaleFromDB(pairs);
                    }
                })
        );
    }


}
