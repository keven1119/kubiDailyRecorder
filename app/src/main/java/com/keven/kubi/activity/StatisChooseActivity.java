package com.keven.kubi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.keven.kubi.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by keven-liang on 2018/5/20.
 */

public class StatisChooseActivity extends BaseAppCompatActivity {

    @BindView(R.id.button_day_statis)
    Button buttonDayStatis;
    @BindView(R.id.button_week_statis)
    Button buttonWeekStatis;
    private long fid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statis_choose);
        ButterKnife.bind(this);
        fid = getIntent().getLongExtra("fid",0);
        if(fid == 0){
            finish();
        }
    }

    @OnClick({R.id.button_day_statis, R.id.button_week_statis})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_day_statis:
                Intent intent = new Intent(this, SaleStatisActivity.class);
                intent.putExtra(SaleStatisActivity.FID, fid);
                intent.putExtra(SaleStatisActivity.CHART_TYPE, SaleStatisActivity.FOOD_PREDAY);
                startActivity(intent);
                break;
            case R.id.button_week_statis:
                Intent intent1 = new Intent(this, SaleStatisActivity.class);
                intent1.putExtra(SaleStatisActivity.FID, fid);
                intent1.putExtra(SaleStatisActivity.CHART_TYPE, SaleStatisActivity.FOOD_PREHOUR);
                startActivity(intent1);
                break;
        }
    }
}
