package com.keven.kubi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.widget.Button;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.keven.kubi.R;
import com.keven.kubi.activity.adapter.SaleStatisAdapter;
import com.keven.kubi.activity.view.SaleStatisView;
import com.keven.kubi.presenter.SaleStatisPresenter;
import com.keven.kubi.weight.InteraptorLinearLayout;

import java.util.List;
import java.util.Observable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by keven-liang on 2018/5/4.
 */

public class SaleStatisActivity extends BaseAppCompatActivity implements SaleStatisView {


    public static final int FOOD_PREDAY = 1; //产品每日销量图
    public static final int FOOD_PREHOUR = 2; //产品每小时销量图
    public static final String CHART_TYPE = "chart_type";
    public static final String FID = "fid";
    public static final String SEARCH_WEEKDAY = "search_weekday";

    @BindView(R.id.recyclerview_sale_statis)
    RecyclerView recyclerviewSaleStatis;
    @BindView(R.id.btn_chart_shrink)
    Button btnChartShrink;
    @BindView(R.id.btn_chart_zoom)
    Button btnChartZoom;
    @BindView(R.id.button_detail_close)
    Button buttonDetailClose;
    @BindView(R.id.interaptorlinearlayout_detail_container)
    InteraptorLinearLayout interaptorlinearlayoutDetailContainer;
    @BindView(R.id.linechart_detail_statis)
    LineChart linechartDetailStatis;
    private GridLayoutManager mGridLayoutManager;
    private SaleStatisAdapter mSaleStatisAdapter;
    private SaleStatisPresenter mSaleStatisPresenter;

    private int chartType;
    private long fid;

    private int zoom = 1;
    private Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_statis);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        fid = intent.getLongExtra(FID, 0);
        if (fid == 0) {
            finish();
        }

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                LineData lineData = (LineData)msg.obj;

                linechartDetailStatis.setData(lineData);
                linechartDetailStatis.postInvalidate();

            }
        };


        mSaleStatisPresenter = new SaleStatisPresenter(this);
        mGridLayoutManager = new GridLayoutManager(this, 1);
        recyclerviewSaleStatis.setLayoutManager(mGridLayoutManager);

        mSaleStatisAdapter = new SaleStatisAdapter(this);
        mSaleStatisAdapter.setOnChartClickListener(new SaleStatisAdapter.OnChartClickListener() {
            @Override
            public void onChartClickedListener(LineData lineData) {
                interaptorlinearlayoutDetailContainer.show();
                Message message = handler.obtainMessage();
                message.obj = lineData;
                handler.sendMessageDelayed(message, 1000);
            }
        });
        recyclerviewSaleStatis.setAdapter(mSaleStatisAdapter);

        chartType = intent.getIntExtra(CHART_TYPE, 0);
        if (chartType == 0) {
            finish();
        }

        interaptorlinearlayoutDetailContainer.close();

        getChartData();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mSaleStatisPresenter!= null){
            mSaleStatisPresenter.unsubscribe();
            mSaleStatisPresenter= null;
        }
    }

    private void getChartData() {

        if (zoom < 1) {
            zoom = 1;
        }

        if (chartType == FOOD_PREHOUR) {

            mSaleStatisPresenter.getProductionHourlySale(fid, 0, zoom);
        } else if (chartType == FOOD_PREDAY) {

            mSaleStatisPresenter.getProductionDailySale(fid, zoom);

        }
    }

    @Override
    public void onWeekSaleDateFromDb(long fid, List<Pair<String, List<Entry>>> pairs) {

        if (pairs != null) {
            mSaleStatisAdapter.setType(1);
            mSaleStatisAdapter.setEntryList(pairs);
            zoom = pairs.size();
        }
    }

    @Override
    public void onWeekDayHourSaleFromDB(List<Pair<String, List<Entry>>> pairs) {
        if (pairs != null) {
            mSaleStatisAdapter.setType(2);
            mSaleStatisAdapter.setEntryList(pairs);
            zoom = pairs.size();
        }

    }

    @OnClick(R.id.btn_chart_shrink)
    public void onBtnChartShrinkClicked() {

        zoom++;

        if (zoom >= 2) {
            mGridLayoutManager.setSpanCount(2);
        } else if (zoom > 0) {
            mGridLayoutManager.setSpanCount(1);
        }
        getChartData();
    }

    @OnClick(R.id.btn_chart_zoom)
    public void onBtnChartZoomClicked() {

        zoom--;

        if (zoom >= 2) {
            mGridLayoutManager.setSpanCount(2);
        } else if (zoom > 0) {
            mGridLayoutManager.setSpanCount(1);
        } else {
            mGridLayoutManager.setSpanCount(1);
        }

        getChartData();

    }

    @OnClick(R.id.button_detail_close)
    public void onViewClicked() {
        interaptorlinearlayoutDetailContainer.close();
    }

}
