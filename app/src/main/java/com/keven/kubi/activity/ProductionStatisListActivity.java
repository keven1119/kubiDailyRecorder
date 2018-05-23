package com.keven.kubi.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keven.kubi.R;
import com.keven.kubi.activity.view.AddFoodView;
import com.keven.kubi.bean.FoodProductionBean;
import com.keven.kubi.presenter.AddFoodPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by keven-liang on 2018/5/20.
 */

public class ProductionStatisListActivity extends BaseAppCompatActivity implements AddFoodView {

    @BindView(R.id.recyclerview_production_choose)
    RecyclerView recyclerviewProductionChoose;
    private LinearLayoutManager linearLayoutManager;
    private ProductionStatisAdapter productionStatisAdapter;
    private AddFoodPresenter mAddFoodPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_production_statis);
        ButterKnife.bind(this);

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerviewProductionChoose.setLayoutManager(linearLayoutManager);

        productionStatisAdapter = new ProductionStatisAdapter(this);
        recyclerviewProductionChoose.setAdapter(productionStatisAdapter);

        mAddFoodPresenter = new AddFoodPresenter(this);

        mAddFoodPresenter.getAllFoodFromDB();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mAddFoodPresenter != null){
            mAddFoodPresenter.unsubscribe();
            mAddFoodPresenter = null;
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
            productionStatisAdapter.setFoodProductionBeanList(foodProductionBeans);
    }

    @Override
    public void onUpdateFoodFail() {

    }

    @Override
    public void onUpdateFoodSuccess(FoodProductionBean foodProductionBean) {

    }

    public static class ProductionStatisAdapter extends RecyclerView.Adapter {

        public LayoutInflater layoutInflater;
        private List<FoodProductionBean> foodProductionBeanList = new ArrayList<>();

        public ProductionStatisAdapter(Context context) {
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflate = layoutInflater.inflate(R.layout.production_statis_item, parent, false);
            return new ProductionStatisHolder(inflate);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            FoodProductionBean foodProductionBean = foodProductionBeanList.get(position);
            ((ProductionStatisHolder)holder).setProduction(foodProductionBean);
        }

        @Override
        public int getItemCount() {
            return foodProductionBeanList.size();
        }

        public void setFoodProductionBeanList(List<FoodProductionBean> foodProductionBeanList) {
            this.foodProductionBeanList = foodProductionBeanList;
            notifyDataSetChanged();
        }
    }

    public static class ProductionStatisHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.relativelayout_production_container)
        RelativeLayout relativelayout_production_container;
        @BindView(R.id.textview_production_name)
        TextView textviewProductionName;
        private FoodProductionBean foodProductionBean;

        public ProductionStatisHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void setProduction(FoodProductionBean foodProductionBean){
            this.foodProductionBean = foodProductionBean;
            textviewProductionName.setText(foodProductionBean.getName());
        }

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.relativelayout_production_container){
                Context context = v.getContext();
                Intent intent = new Intent(context, StatisChooseActivity.class);
                intent.putExtra("fid", foodProductionBean.getFid());

                context.startActivity(intent);

            }
        }
    }
}
