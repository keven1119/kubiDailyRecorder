package com.keven.kubi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.keven.kubi.R;
import com.keven.kubi.dao.LoginDao;
import com.keven.kubi.services.AlarmService;
import com.keven.kubi.utils.ThreadManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by keven-liang on 2018/4/30.
 */

public class FunctionSelectedActivity extends AppCompatActivity {

    @BindView(R.id.button_write_statis)
    Button buttonWriteStatis;
    @BindView(R.id.button_add_newFood)
    Button buttonAddNewFood;
    @BindView(R.id.button_login_out)
    Button buttonLoginOut;
    @BindView(R.id.button_add_product)
    Button buttonAddProduct;
    @BindView(R.id.button_to_statis)
    Button buttonToStatis;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function);
        ButterKnife.bind(this);

        ThreadManager.getInstance().newJoyrunExecutorService().execute(new AlarmService());
    }


    @OnClick({R.id.button_write_statis, R.id.button_add_newFood, R.id.button_login_out, R.id.button_add_product,R.id.button_to_statis})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.button_to_statis:
                Intent intent = new Intent(this, ProductionStatisListActivity.class);
                startActivity(intent);
                break;
            case R.id.button_write_statis:
                Intent intent4 = new Intent(this, WriteStatisActivity.class);
                startActivity(intent4);
                break;
            case R.id.button_add_newFood:
                Intent intent1 = new Intent(this, AddFoodActivity.class);
                startActivity(intent1);
                break;
            case R.id.button_login_out:
                new LoginDao().clearLoginUser();
                Intent intent2 = new Intent(this, LoginActivity.class);
                startActivity(intent2);
                break;

            case R.id.button_add_product:
                Intent intent3 = new Intent(this, AddDailyProductActivity.class);
                startActivity(intent3);

                break;

        }
    }
}
