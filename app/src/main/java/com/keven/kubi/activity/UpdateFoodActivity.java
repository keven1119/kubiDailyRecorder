package com.keven.kubi.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.keven.kubi.MainApp;
import com.keven.kubi.R;
import com.keven.kubi.activity.view.AddFoodView;
import com.keven.kubi.bean.FoodProductionBean;
import com.keven.kubi.dao.FoodProductionDao;
import com.keven.kubi.presenter.AddFoodPresenter;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 更新产品数据
 * Created by keven-liang on 2018/5/2.
 */

public class UpdateFoodActivity extends BaseAppCompatActivity implements AddFoodView {

    @BindView(R.id.textview_name)
    EditText textviewName;
    @BindView(R.id.textview_type)
    EditText textviewType;
    @BindView(R.id.textview_price)
    EditText textviewPrice;
    @BindView(R.id.button_cancel)
    Button buttonCancel;
    @BindView(R.id.button_update)
    Button buttonUpdate;

    private long fid;

    private AddFoodPresenter addFoodPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_food);
        ButterKnife.bind(this);

        addFoodPresenter = new AddFoodPresenter(this);

        fid = getIntent().getLongExtra("fid",-1);
        if(fid >= 0){
            FoodProductionBean foodProductionBean = new FoodProductionDao().queryFoodByFid(fid);

            if(foodProductionBean != null) {
                textviewName.setText(foodProductionBean.getName());
                textviewType.setText(foodProductionBean.getType());
                textviewPrice.setText(foodProductionBean.getPrice()+"");
            }
        }


    }

    @OnClick({R.id.textview_name, R.id.textview_type, R.id.textview_price, R.id.button_cancel, R.id.button_update})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_cancel:
                finish();
                break;
            case R.id.button_update:

                Editable name = textviewName.getText();
                Editable type = textviewType.getText();
                Editable price = textviewPrice.getText();
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(type) || TextUtils.isEmpty(price)) {
                    Toast.makeText(this, R.string.set_food_mgs, Toast.LENGTH_LONG).show();
                    return;
                }

                FoodProductionBean foodProductionBean = new FoodProductionBean();
                foodProductionBean.setFid(fid);
                foodProductionBean.setName(name.toString());
                foodProductionBean.setPrice(Double.parseDouble(price.toString()));
                foodProductionBean.setType(type.toString());


                addFoodPresenter.updateFoodMsg(foodProductionBean);
                break;
        }
    }

    @Override
    public void onAddedSuccess() {

    }

    @Override
    public void onAddedFailed() {

    }

    @Override
    public void onGetAllFoodFromDBFail() {

    }

    @Override
    public void onGetAllFoodFromDB(List<FoodProductionBean> foodProductionBeans) {

    }

    @Override
    public void onUpdateFoodFail() {

    }

    @Override
    public void onUpdateFoodSuccess(FoodProductionBean foodProductionBean) {
        setResult(RESULT_OK);
        finish();
    }
}
