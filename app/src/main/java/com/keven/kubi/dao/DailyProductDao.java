package com.keven.kubi.dao;

import com.keven.kubi.bean.DailyProductBean;
import com.keven.kubi.db.greendao.DailyProductBeanDao;
import com.keven.kubi.db.greendao.DaoSession;

import java.util.Iterator;
import java.util.List;

/**
 * Created by keven-liang on 2018/5/11.
 */

public class DailyProductDao extends BaseDao<DailyProductBeanDao> {

    @Override
    protected DailyProductBeanDao getMyBeanDao(DaoSession daoSession) {
        return daoSession.getDailyProductBeanDao();
    }

    public DailyProductBean DailyProductByFidAndDate(Long fid, Long date){
        DailyProductBean unique = myBeanDao.queryBuilder().where(DailyProductBeanDao.Properties.Fid.eq(fid),
                DailyProductBeanDao.Properties.DateTime.eq(date))
                .build().unique();

        if (unique != null && unique.getFoodProductionBean() == null){
            return null;
        }

        return unique;
    }

    public void insertOrUpdataDailyProduct(DailyProductBean dailyProduction){
        DailyProductBean unique = myBeanDao.queryBuilder()
                .where(DailyProductBeanDao.Properties.Fid.eq(dailyProduction.getFid()), DailyProductBeanDao.Properties.DateTime.eq(dailyProduction.getDateTime())).unique();

        if(unique == null){
            myBeanDao.insert(dailyProduction);
        }else {
            myBeanDao.update(dailyProduction);
        }
    }

    /**
     * 查询某个产品今天的产量
     * @param fid
     * @param todayStartTimeStamp
     * @return
     */
    public DailyProductBean queryDailyProductByFid(long fid, long todayStartTimeStamp){
        DailyProductBean unique = myBeanDao.queryBuilder()
                .where(DailyProductBeanDao.Properties.Fid.eq(fid), DailyProductBeanDao.Properties.DateTime.eq(todayStartTimeStamp))
                .unique();

        if (unique != null && unique.getFoodProductionBean() == null){
            return null;
        }
        return unique;

    }

    /**
     * 查询当天所有产品的产量
     * @param todayStartTimeStamp
     * @return
     */
    public List<DailyProductBean> queryAllDailyProduct(long todayStartTimeStamp){
        List<DailyProductBean> list = myBeanDao.queryBuilder()
                .where(DailyProductBeanDao.Properties.DateTime.eq(todayStartTimeStamp))
                .list();

        Iterator<DailyProductBean> iterator =
                list.iterator();

        while (iterator.hasNext()){
            DailyProductBean next = iterator.next();
            if(next.getFoodProductionBean() == null){
                iterator.remove();
            }
        }


        return list;
    }
}
