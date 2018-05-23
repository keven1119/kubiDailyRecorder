package com.keven.kubi.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 记载产品信息
 * Created by keven-liang on 2018/4/30.
 */

@Entity
public class FoodProductionBean {

    @Id(autoincrement = true)
    private Long fid ;
    @Unique
    @NotNull
    private String name = "";
    @NotNull
    private String type = "";
    @NotNull
    private double price = 0d;




    @Generated(hash = 1930214421)
    public FoodProductionBean(Long fid, @NotNull String name, @NotNull String type,
            double price) {
        this.fid = fid;
        this.name = name;
        this.type = type;
        this.price = price;
    }
    @Generated(hash = 1615158148)
    public FoodProductionBean() {
    }


   

    public double getPrice() {
        return this.price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Long getFid() {
        return fid;
    }

    public void setFid(Long fid) {
        this.fid = fid;
    }


}
