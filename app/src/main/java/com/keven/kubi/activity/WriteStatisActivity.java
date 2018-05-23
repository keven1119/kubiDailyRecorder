package com.keven.kubi.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.keven.kubi.R;
import com.keven.kubi.activity.adapter.WriteStatisAdapter;
import com.keven.kubi.activity.view.WriteStatisView;
import com.keven.kubi.bean.FoodHourBean;
import com.keven.kubi.presenter.WriteStatisPresenter;
import com.keven.kubi.utils.ThreadManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 填写每小时点心的剩余量页面
 */
public class WriteStatisActivity extends BaseAppCompatActivity implements WriteStatisView {


    @BindView(R.id.recyclerview_statistics)
    RecyclerView recyclerviewStatistics;
    private WriteStatisAdapter mWriteStatisAdapter;
    private WriteStatisPresenter mWriteStatisPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setTitle(R.string.write_hour_statis);
        mWriteStatisPresenter = new WriteStatisPresenter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerviewStatistics.setLayoutManager(linearLayoutManager);

        mWriteStatisAdapter = new WriteStatisAdapter(this);
        mWriteStatisAdapter.setOnCountInputRecordListener(new WriteStatisAdapter.OnCountInputRecordListener() {
            @Override
            public void OnCountInputRecorded(FoodHourBean hourBean, int inputAmount) {
                    mWriteStatisPresenter.saveHourSaleData(hourBean,inputAmount);
            }
        });

        recyclerviewStatistics.setAdapter(mWriteStatisAdapter);
        mWriteStatisPresenter.getFoodHourSaleMsg();

    }

    @Override
    public void onLoadAllFoodTypeFail() {

    }

    @Override
    public void onLoadAllFoodType(List<FoodHourBean> foodHourBeans) {
        mWriteStatisAdapter.setFoodHourBeanList(foodHourBeans);
    }

    @Override
    public void onSaveHourDataSuc(FoodHourBean foodHourBean) {
        mWriteStatisPresenter.getFoodHourSaleMsg();
    }

    @Override
    public void onSaveHourDataFail(Throwable e) {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
    }

}
