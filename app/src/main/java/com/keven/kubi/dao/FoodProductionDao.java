package com.keven.kubi.dao;

import com.keven.kubi.bean.FoodProductionBean;
import com.keven.kubi.db.greendao.DaoSession;
import com.keven.kubi.db.greendao.FoodProductionBeanDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by keven-liang on 2018/5/12.
 */

public class FoodProductionDao extends BaseDao<FoodProductionBeanDao> {
    @Override
    protected FoodProductionBeanDao getMyBeanDao(DaoSession daoSession) {
        return daoSession.getFoodProductionBeanDao();
    }

    public List<FoodProductionBean> getAllFoodProduction(){
       return myBeanDao.loadAll();
    }

    public FoodProductionBean queryFoodByName(String name){
        QueryBuilder<FoodProductionBean> queryBuilder = myBeanDao.queryBuilder();
        FoodProductionBean unique = queryBuilder.where(FoodProductionBeanDao.Properties.Name.eq(name)).unique();

        return unique;
    }

    public FoodProductionBean queryFoodByFid(long fid){
        QueryBuilder<FoodProductionBean> queryBuilder = myBeanDao.queryBuilder();
        FoodProductionBean unique = queryBuilder.where(FoodProductionBeanDao.Properties.Fid.eq(fid)).unique();

        return unique;
    }

    public void delete(FoodProductionBean foodProductionBean){
        myBeanDao.delete(foodProductionBean);
    }

    public long insert(FoodProductionBean foodProductionBean){
        long insert = myBeanDao.insert(foodProductionBean);
        return insert;
    }

    public void update(FoodProductionBean foodProductionBean){
        myBeanDao.update(foodProductionBean);
    }
}
