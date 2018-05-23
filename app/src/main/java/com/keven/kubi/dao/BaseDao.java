package com.keven.kubi.dao;

import com.keven.kubi.MainApp;
import com.keven.kubi.db.greendao.DaoSession;

import org.greenrobot.greendao.AbstractDao;

import java.util.Collection;

/**
 * Created by keven-liang on 2018/5/11.
 */

public abstract class BaseDao<T extends AbstractDao>{

    protected T myBeanDao;

    public BaseDao(){
        DaoSession daoSession = MainApp.getInstances().getDaoSession();
        myBeanDao = getMyBeanDao(daoSession);
    }

    protected abstract T getMyBeanDao(DaoSession daoSession);



}
