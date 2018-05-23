package com.keven.kubi.dao;

import com.keven.kubi.db.greendao.DaoSession;
import com.keven.kubi.db.greendao.HourlyAlarmBeanDao;

/**
 * Created by keven-liang on 2018/5/12.
 */

public class HourlyAlarmDao extends BaseDao<HourlyAlarmBeanDao> {
    @Override
    protected HourlyAlarmBeanDao getMyBeanDao(DaoSession daoSession) {
        return daoSession.getHourlyAlarmBeanDao();
    }

}
