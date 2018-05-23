package com.keven.kubi.activity.view;

import android.util.Pair;

import com.github.mikephil.charting.data.Entry;
import com.keven.kubi.bean.FoodHourBean;
import com.keven.kubi.bean.ProductionDailySaleBean;

import java.util.List;

/**
 * Created by keven-liang on 2018/5/12.
 */

public interface SaleStatisView {
    void onWeekSaleDateFromDb(long fid, List<Pair<String, List<Entry>>> pairs);

    void onWeekDayHourSaleFromDB(List<Pair<String, List<Entry>>> pairs);
}
