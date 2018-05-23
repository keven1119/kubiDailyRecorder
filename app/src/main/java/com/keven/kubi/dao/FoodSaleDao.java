package com.keven.kubi.dao;

import com.keven.kubi.bean.FoodHourBean;
import com.keven.kubi.bean.ProductionDailySaleBean;
import com.keven.kubi.db.greendao.DaoSession;
import com.keven.kubi.db.greendao.FoodHourBeanDao;

import java.util.Iterator;
import java.util.List;

/**
 * Created by keven-liang on 2018/5/12.
 */

public class FoodSaleDao extends BaseDao<FoodHourBeanDao> {
    @Override
    protected FoodHourBeanDao getMyBeanDao(DaoSession daoSession) {
        return daoSession.getFoodHourBeanDao();
    }

    public void insertOrUpdateHourSale(FoodHourBean foodHourBean){
        FoodHourBean unique = myBeanDao.queryBuilder().where(FoodHourBeanDao.Properties.Fid.eq(foodHourBean.getFid()),
                FoodHourBeanDao.Properties.StartTimeStamp.eq(foodHourBean.getStartTimeStamp()),
                FoodHourBeanDao.Properties.EndTimeStamp.eq(foodHourBean.getEndTimeStamp())).unique();

        if(unique == null) {
            myBeanDao.insert(foodHourBean);
        }else {
            myBeanDao.update(foodHourBean);
        }
    }

    public ProductionDailySaleBean queryDaySale( long fid, final long dayStartTime){

        long dayEndTime = dayStartTime + 24 * 3600 * 1000;
        List<FoodHourBean> list = myBeanDao.queryBuilder().where(FoodHourBeanDao.Properties.Fid.eq(fid),
                FoodHourBeanDao.Properties.StartTimeStamp.ge(dayStartTime),
                FoodHourBeanDao.Properties.EndTimeStamp.le(dayEndTime))
                .list();



        ProductionDailySaleBean productionDailySaleBean = new ProductionDailySaleBean();
        productionDailySaleBean.setFid(fid);


        int daySaleCount = 0;


        for (FoodHourBean foodHourBean : list){
            if(foodHourBean.getFoodProductionBean() == null){
                return productionDailySaleBean;
            }
            productionDailySaleBean.setName(foodHourBean.getName());
            daySaleCount = foodHourBean.getHourScaleCount() + daySaleCount;
        }

        productionDailySaleBean.setDayScaleCount(daySaleCount);


        return productionDailySaleBean;

    }

    public FoodHourBean queryHourSale(long fid, long hourStartTime){
        FoodHourBean unique = myBeanDao.queryBuilder().where(FoodHourBeanDao.Properties.Fid.eq(fid), FoodHourBeanDao.Properties.StartTimeStamp.eq(hourStartTime))
                .unique();

        if(unique != null && unique.getFoodProductionBean() == null){
            return null;
        }

        return unique;
    }

    /**
     * 查询某个产品某个时间段内的总销量
     * @param fid
     * @param startTime    开始时间戳
     * @param endTime   结束时间戳
     * @return
     */
    public List<FoodHourBean> queryProductionSaleCount(long fid, long startTime, long endTime){
        List<FoodHourBean> list = myBeanDao.queryBuilder().where(FoodHourBeanDao.Properties.Fid.eq(fid),
                FoodHourBeanDao.Properties.StartTimeStamp.ge(startTime),
                FoodHourBeanDao.Properties.StartTimeStamp.le(endTime))
                .list();
        Iterator<FoodHourBean> iterator = list.iterator();
        while (iterator.hasNext()){
            FoodHourBean next = iterator.next();
            if(next.getFoodProductionBean() == null){
                iterator.remove();
            }
        }

        return list;
    }


    /**
     * 查询所有产品某个时间段内的总销量
     * @param startTime    开始时间戳
     * @param endTime   结束时间戳
     * @return
     */
    public List<FoodHourBean> queryAllProductionSaleCount( long startTime, long endTime){
        List<FoodHourBean> list = myBeanDao.queryBuilder().where(FoodHourBeanDao.Properties.StartTimeStamp.eq(startTime),
                FoodHourBeanDao.Properties.EndTimeStamp.eq(endTime))
                .list();

        Iterator<FoodHourBean> iterator = list.iterator();
        while (iterator.hasNext()){
            FoodHourBean next = iterator.next();
            if(next.getFoodProductionBean() == null){
                iterator.remove();
            }
        }

        return list;
    }

}
