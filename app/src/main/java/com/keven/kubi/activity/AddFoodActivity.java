package com.keven.kubi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.keven.kubi.R;
import com.keven.kubi.activity.adapter.FoodAddedAdapter;
import com.keven.kubi.activity.view.AddFoodView;
import com.keven.kubi.bean.FoodProductionBean;
import com.keven.kubi.presenter.AddFoodPresenter;
import com.keven.kubi.utils.AppUtils;
import com.thejoyrun.noticefinder.NoticeFinder;
import com.thejoyrun.noticefinder.annotation.OnNotice;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by keven-liang on 2018/4/30.
 */

public class AddFoodActivity extends BaseAppCompatActivity implements AddFoodView {


    public final static int REQUEST_CODE = 1;

    @BindView(R.id.textview_name)
    EditText textviewName;
    @BindView(R.id.textview_type)
    EditText textviewType;
    @BindView(R.id.textview_price)
    EditText textviewPrice;
    @BindView(R.id.relativelayout_name_container)
    RelativeLayout relativelayoutNameContainer;
    @BindView(R.id.button_sure_add)
    Button buttonSureAdd;
    @BindView(R.id.recyclerview_food_type)
    RecyclerView recyclerviewFoodType;
    private FoodAddedAdapter mFoodAddedAdapter;

    private AddFoodPresenter mAddFoodPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        ButterKnife.bind(this);
        NoticeFinder.inject(this);
        mAddFoodPresenter = new AddFoodPresenter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerviewFoodType.setLayoutManager(linearLayoutManager);
        mFoodAddedAdapter = new FoodAddedAdapter(this);
        recyclerviewFoodType.setAdapter(mFoodAddedAdapter);
        setTitle(R.string.write_hour_statis);



        mAddFoodPresenter.getAllFoodFromDB();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            mAddFoodPresenter.getAllFoodFromDB();
        }
    }

    @OnClick(R.id.button_sure_add)
    public void onViewClicked() {

        Editable name = textviewName.getText();
        Editable type = textviewType.getText();
        Editable price = textviewPrice.getText();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(type) || TextUtils.isEmpty(price)) {
            Toast.makeText(this, R.string.set_food_mgs, Toast.LENGTH_LONG).show();
            return;
        }

        FoodProductionBean foodProductionBean = new FoodProductionBean();
        foodProductionBean.setName(name.toString());
        foodProductionBean.setType(type.toString());
        foodProductionBean.setPrice(Double.parseDouble(price.toString()));

        mAddFoodPresenter.addNewFood(foodProductionBean);

    }

    @OnNotice
    public void updateAddedFoodList() {
        mAddFoodPresenter.getAllFoodFromDB();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NoticeFinder.unInject(this);
    }

    @Override
    public void onAddedSuccess() {
        Toast.makeText(this, R.string.food_add_success, Toast.LENGTH_LONG).show();
        mAddFoodPresenter.getAllFoodFromDB();
    }

    @Override
    public void onAddedFailed() {
        Toast.makeText(this, R.string.food_add_fail, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onGetAllFoodFromDBFail() {

    }

    @Override
    public void onGetAllFoodFromDB(List<FoodProductionBean> foodProductionBeans) {
        mFoodAddedAdapter.setFoodProductionBeanList(foodProductionBeans);
    }

    @Override
    public void onUpdateFoodFail() {

    }

    @Override
    public void onUpdateFoodSuccess(FoodProductionBean foodProductionBean) {

    }
}
