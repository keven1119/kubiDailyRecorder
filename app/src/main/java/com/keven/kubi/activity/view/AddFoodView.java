package com.keven.kubi.activity.view;

import com.keven.kubi.bean.FoodProductionBean;

import java.util.List;

/**
 * Created by keven-liang on 2018/4/30.
 */

public interface AddFoodView {
    void onAddedSuccess();

    void onAddedFailed();

    void onGetAllFoodFromDBFail();

    void onGetAllFoodFromDB(List<FoodProductionBean> foodProductionBeans);

    void onUpdateFoodFail();

    void onUpdateFoodSuccess(FoodProductionBean foodProductionBean);
}
