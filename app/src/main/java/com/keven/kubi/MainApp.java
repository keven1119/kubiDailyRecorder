package com.keven.kubi;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import com.growingio.android.sdk.collection.Configuration;
import com.growingio.android.sdk.collection.GrowingIO;
import com.keven.kubi.db.GreenDaoHelper;
import com.keven.kubi.db.greendao.DaoMaster;
import com.keven.kubi.db.greendao.DaoSession;

/**
 * Created by keven-liang on 2018/4/28.
 */

public class MainApp extends Application {


    private GreenDaoHelper mHelper;
    //private Helper mHelper;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    public static MainApp instances;

    @Override
    public void onCreate() {
        super.onCreate();
        instances = this;
        GrowingIO.startWithConfiguration(this, new Configuration()
                .useID()
                .trackAllFragments()
        );

        setDatabase();
    }


    public static MainApp getInstances(){
        return instances;
    }

    /**
     * 设置greenDao
     */
    private void setDatabase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        // mHelper = new Helper(new GreenDaoUtils(this));
        mHelper = new GreenDaoHelper(this);
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = mHelper.getDaoMaster();
        mDaoSession = mHelper.getDaoSession();
    }
    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public DaoMaster getDaoMaster() {
        return mDaoMaster;
    }

}
