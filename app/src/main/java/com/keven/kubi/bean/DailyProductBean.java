package com.keven.kubi.bean;

/**
 * Created by keven-liang on 2018/5/11.
 */

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.keven.kubi.db.greendao.DaoSession;
import com.keven.kubi.db.greendao.DailyProductBeanDao;
import com.keven.kubi.db.greendao.FoodProductionBeanDao;

/**
 * 一天生产量
 */
@Entity
public class DailyProductBean {

    @Id(autoincrement = true)
    private Long id;

    @Index
    @NotNull
    private Long fid = 0l;

    @NotNull
    private String name="";

    @Index
    @NotNull
    private Long dateTime = 0l;//当日日期,当日0：00

    @NotNull
    private Integer productCount = 0;//当日产量

    @ToOne (joinProperty = "fid")
    private FoodProductionBean foodProductionBean ;

    @Generated(hash = 1939012073)
    private transient Long foodProductionBean__resolvedKey;

    /** Used for active entity operations. */
    @Generated(hash = 1662637025)
    private transient DailyProductBeanDao myDao;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;


    @Generated(hash = 764332926)
    public DailyProductBean(Long id, @NotNull Long fid, @NotNull String name, @NotNull Long dateTime,
            @NotNull Integer productCount) {
        this.id = id;
        this.fid = fid;
        this.name = name;
        this.dateTime = dateTime;
        this.productCount = productCount;
    }

    @Generated(hash = 1428091081)
    public DailyProductBean() {
    }


    public Long getFid() {
        return fid;
    }

    public void setFid(Long fid) {
        this.fid = fid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getDateTime() {
        return dateTime;
    }

    public void setDateTime(Long dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getProductCount() {
        return productCount;
    }

    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
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
    @Generated(hash = 809314626)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getDailyProductBeanDao() : null;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
}
