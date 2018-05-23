package com.keven.kubi.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.DaoException;

import com.keven.kubi.db.greendao.DaoSession;
import com.keven.kubi.db.greendao.FoodHourBeanDao;
import com.keven.kubi.db.greendao.FoodProductionBeanDao;

/**
 * 用于记载每小时销量的
 * Created by keven-liang on 2018/4/28.
 */
@Entity()
public class FoodHourBean {

    @Id(autoincrement = true)
    private Long id;

    @Index
    @NotNull
    private Long fid = 0l;

    @NotNull
    private String name = "";

    @NotNull
    private int hourScaleCount = -1; //当点产品数量

    @NotNull
    private int residueCount = 0;

    //===========================用于统计写入时间===================
    @NotNull
    private long realityWriteTimeStamp = 0l; //实际做等级的情况

    @Index
    @NotNull
    private long predictWriteTimeStamp = 0l; //预计写入时间
    //===========================用于统计写入时间===================


    @NotNull
    private long weekStartTimeStamp = 0l;


    @Index
    @NotNull
    private long startTimeStamp = 0l; //开始统计时间

    @Index
    @NotNull
    private long endTimeStamp = 0l; //结束统计时间

    @ToOne (joinProperty = "fid")
    private FoodProductionBean foodProductionBean ;

    @Generated(hash = 1939012073)
    private transient Long foodProductionBean__resolvedKey;

    /** Used for active entity operations. */
    @Generated(hash = 674886814)
    private transient FoodHourBeanDao myDao;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;





    @Generated(hash = 1192012979)
    public FoodHourBean(Long id, @NotNull Long fid, @NotNull String name, int hourScaleCount,
            int residueCount, long realityWriteTimeStamp, long predictWriteTimeStamp,
            long weekStartTimeStamp, long startTimeStamp, long endTimeStamp) {
        this.id = id;
        this.fid = fid;
        this.name = name;
        this.hourScaleCount = hourScaleCount;
        this.residueCount = residueCount;
        this.realityWriteTimeStamp = realityWriteTimeStamp;
        this.predictWriteTimeStamp = predictWriteTimeStamp;
        this.weekStartTimeStamp = weekStartTimeStamp;
        this.startTimeStamp = startTimeStamp;
        this.endTimeStamp = endTimeStamp;
    }

    @Generated(hash = 147718801)
    public FoodHourBean() {
    }



   

    public int getHourScaleCount() {
        return this.hourScaleCount;
    }

    public void setHourScaleCount(int hourScaleCount) {
        this.hourScaleCount = hourScaleCount;
    }

    public Long getFid() {
        return this.fid;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFid(Long fid) {
        this.fid = fid;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1118414530)
    public void setFoodProductionBean(@NotNull FoodProductionBean foodProductionBean) {
        if (foodProductionBean == null) {
            throw new DaoException(
                    "To-one property 'fid' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.foodProductionBean = foodProductionBean;
            fid = foodProductionBean.getFid();
            foodProductionBean__resolvedKey = fid;
        }
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 2029060010)
    public FoodProductionBean getFoodProductionBean() {
        long __key = this.fid;
        if (foodProductionBean__resolvedKey == null
                || !foodProductionBean__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            FoodProductionBeanDao targetDao = daoSession.getFoodProductionBeanDao();
            FoodProductionBean foodProductionBeanNew = targetDao.load(__key);
            synchronized (this) {
                foodProductionBean = foodProductionBeanNew;
                foodProductionBean__resolvedKey = __key;
            }
        }
        return foodProductionBean;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1697479823)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getFoodHourBeanDao() : null;
    }

    public long getStartTimeStamp() {
        return startTimeStamp;
    }

    public void setStartTimeStamp(long startTimeStamp) {
        this.startTimeStamp = startTimeStamp;

        //一小时结束时间
        long endTimeStamp = startTimeStamp + 3600 * 1000;
        setEndTimeStamp(endTimeStamp);
    }

    public long getEndTimeStamp() {
        return endTimeStamp;
    }

    public void setEndTimeStamp(long endTimeStamp) {
        this.endTimeStamp = endTimeStamp;
    }

    public long getRealityWriteTimeStamp() {
        return realityWriteTimeStamp;
    }

    public void setRealityWriteTimeStamp(long realityWriteTimeStamp) {
        this.realityWriteTimeStamp = realityWriteTimeStamp;
    }

    public long getPredictWriteTimeStamp() {
        return predictWriteTimeStamp;
    }

    public void setPredictWriteTimeStamp(long predictWriteTimeStamp) {
        this.predictWriteTimeStamp = predictWriteTimeStamp;
    }

    public long getWeekStartTimeStamp() {
        return weekStartTimeStamp;
    }

    public void setWeekStartTimeStamp(long weekStartTimeStamp) {
        this.weekStartTimeStamp = weekStartTimeStamp;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getResidueCount() {
        return residueCount;
    }

    public void setResidueCount(int residueCount) {
        this.residueCount = residueCount;
    }
}
