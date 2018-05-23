package com.keven.kubi.bean;

/**
 *
 * 一天的销量
 * Created by keven-liang on 2018/5/12.
 */

public class ProductionDailySaleBean  {

    private Long fid = 0l;
    private String name;
    private int dayScaleCount = 0; //当天产品数量


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

    public int getDayScaleCount() {
        return dayScaleCount;
    }

    public void setDayScaleCount(int dayScaleCount) {
        this.dayScaleCount = dayScaleCount;
    }
}
