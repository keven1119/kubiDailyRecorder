package com.keven.kubi.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.keven.kubi.MainApp;
import com.keven.kubi.db.greendao.DaoMaster;
import com.keven.kubi.db.greendao.DaoSession;
import com.keven.kubi.utils.LogUtils;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.StandardDatabase;
import org.greenrobot.greendao.query.QueryBuilder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public class GreenDaoHelper extends DaoMaster.OpenHelper{

    private  DaoMaster daoMaster;
    private  DaoSession daoSession;
    private SQLiteDatabase db;

    public static final String DBNAME = "greendao.db";

    public GreenDaoHelper(Context context){
        super(context,DBNAME,null);
        db = getWritableDatabase();
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {

        Collection<? extends AbstractDao<?, ?>> allDaos = getDaoSession().getAllDaos();

        ArrayList<? extends AbstractDao<?, ?>> arrayList = new ArrayList(allDaos);

        Class<? extends AbstractDao<?,?>>[] classes = new Class[allDaos.size()];

        for (int i = 0; i< classes.length ;i++){
            classes[i] = (Class<? extends AbstractDao<?, ?>>) arrayList.get(i).getClass();
        }
        MigrationHelper.getInstance().migrate(db,classes);
    }

    /**
     * 取得DaoMaster
     *
     * @return
     */
    public  DaoMaster getDaoMaster() {
        if (daoMaster == null) {
            daoMaster = new DaoMaster(db);
        }
        return daoMaster;
    }
//
    /**
     * 取得DaoSession
     *
     * @return
     */
    public  DaoSession getDaoSession() {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster();
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }
}