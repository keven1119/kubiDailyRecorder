package com.keven.kubi.activity.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.keven.kubi.MainApp;
import com.keven.kubi.R;
import com.keven.kubi.activity.AddFoodActivity;
import com.keven.kubi.activity.AddFoodActivity$$ObjectNoticeInter;
import com.keven.kubi.activity.UpdateFoodActivity;
import com.keven.kubi.bean.FoodProductionBean;
import com.keven.kubi.weight.InputAmountDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by keven-liang on 2018/4/30.
 */

public class FoodAddedAdapter extends RecyclerView.Adapter {
    private LayoutInflater layoutInflater;

    private List<FoodProductionBean> foodProductionBeanList = new ArrayList<>();

    public FoodAddedAdapter(Context context){
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = layoutInflater.inflate(R.layout.quantity_statis_item_layout, parent, false);
        return new FoodAddedHolder(inflate);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((FoodAddedHolder)holder).setFoodProduction(foodProductionBeanList.get(position));

    }

    @Override
    public int getItemCount() {
        return foodProductionBeanList.size();
    }

    public void setFoodProductionBeanList(List<FoodProductionBean> foodProductionBeanList) {
        this.foodProductionBeanList = foodProductionBeanList;
        notifyDataSetChanged();
    }

    public List<FoodProductionBean> getFoodProductionBeanList() {
        return foodProductionBeanList;
    }

    public static class FoodAddedHolder extends RecyclerView.ViewHolder implements View.OnClickListener ,
            DialogInterface.OnClickListener{


        @BindView(R.id.textview_number)
        TextView textviewNumber;
        @BindView(R.id.textview_amount_input)
        TextView textviewAmountInput;
        @BindView(R.id.textview_delete_food)
        TextView textviewDeleteFood;
        @BindView(R.id.textview_name)
        TextView textviewName;
        @BindView(R.id.textview_type)
        TextView textviewType;
        @BindView(R.id.textview_price)
        TextView textviewPrice;
        @BindView(R.id.textview_week_sale)
        TextView textviewWeekSale;
        @BindView(R.id.relativelayout_name_container)
        RelativeLayout relativelayoutNameContainer;

        private FoodProductionBean mFoodProductionBean;
        private AlertDialog alertDialog;


        public FoodAddedHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            relativelayoutNameContainer.setOnClickListener(this);
            textviewDeleteFood.setOnClickListener(this);
            textviewAmountInput.setOnClickListener(this);
            textviewAmountInput.setText(R.string.sure_to_update_food);
        }

        public void setFoodProduction(FoodProductionBean foodProduction){
            mFoodProductionBean = foodProduction;
            textviewNumber.setText(getAdapterPosition()+"");
            textviewName.setText(foodProduction.getName());
            textviewPrice.setText(foodProduction.getPrice() + "");
        }

        @Override
        public void onClick(View v) {
            int id = v.getId();

            if(id == R.id.textview_amount_input){
                Activity activity = (Activity) v.getContext();
                Intent intent = new Intent(activity, UpdateFoodActivity.class);
                intent.putExtra("fid", mFoodProductionBean.getFid());
                activity.startActivityForResult(intent, AddFoodActivity.REQUEST_CODE);

            } else if(id == R.id.textview_delete_food){
                if(alertDialog != null){
                    alertDialog.dismiss();
                    alertDialog = null;
                }
                Context context = v.getContext();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                alertDialog = builder.setCancelable(false)
                        .setTitle(R.string.delete_newfood_tips)
                        .setNegativeButton(context.getString(R.string.input_amount_cancel),this)
                        .setPositiveButton(context.getString(R.string.input_amount_sure),this)
                        .create();
                alertDialog.show();

            }else if(id == R.id.relativelayout_name_container){

            }
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            alertDialog.dismiss();
            alertDialog = null;
            if(which == DialogInterface.BUTTON_NEGATIVE){


            }else if(which == DialogInterface.BUTTON_POSITIVE){
                MainApp.getInstances().getDaoSession().getFoodProductionBeanDao().delete(mFoodProductionBean);
                new AddFoodActivity$$ObjectNoticeInter().updateAddedFoodList();
            }

        }
    }
}
