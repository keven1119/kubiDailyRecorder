package com.keven.kubi.activity.view;

import com.keven.kubi.bean.FoodHourBean;

import java.util.List;

/**
 * Created by keven-liang on 2018/4/30.
 */

public interface WriteStatisView {
    void onLoadAllFoodTypeFail();

    void onLoadAllFoodType(List<FoodHourBean> foodHourBeans);

    void onSaveHourDataSuc(FoodHourBean foodHourBean);

    void onSaveHourDataFail(Throwable e);
}
