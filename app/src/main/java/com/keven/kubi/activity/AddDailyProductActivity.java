package com.keven.kubi.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.keven.kubi.R;
import com.keven.kubi.activity.adapter.AddDailyProductAdapter;
import com.keven.kubi.activity.view.AddDailyProductView;
import com.keven.kubi.bean.DailyProductBean;
import com.keven.kubi.presenter.AddDailyProductPresenter;
import com.thejoyrun.noticefinder.NoticeFinder;
import com.thejoyrun.noticefinder.annotation.OnNotice;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by keven-liang on 2018/5/15.
 */

public class AddDailyProductActivity extends BaseAppCompatActivity implements AddDailyProductView{


    @BindView(R.id.recyclerview_add_daily_production)
    RecyclerView recyclerviewAddDailyProduction;
    private LinearLayoutManager mLinearLayoutManager;
    private AddDailyProductAdapter mAddDailyProductAdapter;
    private AddDailyProductPresenter mAddDailyProductPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_daily_production);
        ButterKnife.bind(this);
        NoticeFinder.inject(this);

        setTitle(R.string.add_product_form);

        mAddDailyProductPresenter = new AddDailyProductPresenter(this);

        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerviewAddDailyProduction.setLayoutManager(mLinearLayoutManager);

        mAddDailyProductAdapter = new AddDailyProductAdapter(this);
        recyclerviewAddDailyProduction.setAdapter(mAddDailyProductAdapter);

        mAddDailyProductPresenter.getTodayDailyProduction();

    }

    @OnNotice
    public void updataTodayDailyProduction(){
        mAddDailyProductPresenter.getTodayDailyProduction();
    }

    @Override
    public void onAllDailyProductFromDB(List<DailyProductBean> dailyProductBeans) {

        mAddDailyProductAdapter.setDailyProductBeanList(dailyProductBeans);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NoticeFinder.unInject(this);
        if(mAddDailyProductPresenter != null){
            mAddDailyProductPresenter.unsubscribe();
            mAddDailyProductPresenter = null;
        }
    }
}
