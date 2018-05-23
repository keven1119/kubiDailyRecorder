package com.keven.kubi.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by keven-liang on 2018/5/3.
 */

@Entity
public class HourlyAlarmBean {

    @Id(autoincrement = true)
    private Long id;
    @NotNull
    @Unique
    private Long hourTimeStamp = 0l;//闹钟时间  单位：毫秒
    @NotNull
    private Boolean isRinged = false; //是否响过
    public Boolean getIsRinged() {
        return this.isRinged;
    }
    public void setIsRinged(Boolean isRinged) {
        this.isRinged = isRinged;
    }
    public Long getHourTimeStamp() {
        return this.hourTimeStamp;
    }
    public void setHourTimeStamp(Long hourTimeStamp) {
        this.hourTimeStamp = hourTimeStamp;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 823982276)
    public HourlyAlarmBean(Long id, @NotNull Long hourTimeStamp,
            @NotNull Boolean isRinged) {
        this.id = id;
        this.hourTimeStamp = hourTimeStamp;
        this.isRinged = isRinged;
    }
    @Generated(hash = 1503143994)
    public HourlyAlarmBean() {
    }



}
