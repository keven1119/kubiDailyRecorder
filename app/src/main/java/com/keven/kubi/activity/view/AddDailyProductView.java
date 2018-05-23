package com.keven.kubi.activity.view;

import com.keven.kubi.bean.DailyProductBean;

import java.util.List;

/**
 * Created by keven-liang on 2018/5/15.
 */

public interface AddDailyProductView {
    void onAllDailyProductFromDB(List<DailyProductBean> dailyProductBeans);
}
