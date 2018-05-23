package com.keven.kubi.activity.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.keven.kubi.MainApp;
import com.keven.kubi.R;
import com.keven.kubi.activity.AddDailyProductActivity$$ObjectNoticeInter;
import com.keven.kubi.activity.SaleStatisActivity;
import com.keven.kubi.bean.DailyProductBean;
import com.keven.kubi.bean.FoodProductionBean;
import com.keven.kubi.dao.DailyProductDao;
import com.keven.kubi.dao.FoodSaleDao;
import com.keven.kubi.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by keven-liang on 2018/5/15.
 */

public class AddDailyProductAdapter extends RecyclerView.Adapter {

    private LayoutInflater layoutInflater;

    private List<DailyProductBean> dailyProductBeanList = new ArrayList<>();

    public AddDailyProductAdapter(Context context){
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View inflate = layoutInflater.inflate(R.layout.quantity_statis_item_layout, parent, false);

        return new AddDayProductionHolder(inflate);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DailyProductBean dailyProductBean = dailyProductBeanList.get(position);
        ((AddDayProductionHolder)holder).setDailyProduct(dailyProductBean);

    }

    @Override
    public int getItemCount() {
        return dailyProductBeanList.size();
    }


    public void setDailyProductBeanList(List<DailyProductBean> dailyProductBeanList) {
        this.dailyProductBeanList = dailyProductBeanList;
        notifyDataSetChanged();
    }

    public static class AddDayProductionHolder extends RecyclerView.ViewHolder implements View.OnClickListener ,
            DialogInterface.OnClickListener {


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

        private DailyProductBean mDailyProductBean;
        private AlertDialog mInputAmountDialog;
        private EditText editTextInputAmount;

        private String todayProductStr = "";

        private String clickFillTips = "";
        private int normalColor;

        public AddDayProductionHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            relativelayoutNameContainer.setOnClickListener(this);
            textviewAmountInput.setOnClickListener(this);
            textviewAmountInput.setText(R.string.click_to_add_product);
            normalColor = textviewAmountInput.getCurrentTextColor();
            clickFillTips = textviewAmountInput.getText().toString();
            textviewDeleteFood.setText("");

            todayProductStr = itemView.getContext().getString(R.string.today_product);
        }

        public void setDailyProduct(DailyProductBean dailyProduct) {
            mDailyProductBean = dailyProduct;

            FoodProductionBean foodProductionBean = mDailyProductBean.getFoodProductionBean();
            textviewNumber.setText(getAdapterPosition() + "");
            textviewName.setText(mDailyProductBean.getName());

            textviewPrice.setText(foodProductionBean.getPrice() + "");
            textviewType.setText(foodProductionBean.getType());
            textviewWeekSale.setText(todayProductStr + mDailyProductBean.getProductCount() );

        }

        @Override
        public void onClick(View v) {
            int id = v.getId();

            if (id == R.id.textview_amount_input) {

                if (mInputAmountDialog != null) {
                    mInputAmountDialog.dismiss();
                    mInputAmountDialog = null;
                }

                Context context = v.getContext();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                View inflate = LayoutInflater.from(context).inflate(R.layout.input_amount_layout, null, false);
                editTextInputAmount = inflate.findViewById(R.id.edittext_input_amount);
                editTextInputAmount.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
                mInputAmountDialog = builder.setCancelable(false)
                        .setView(inflate)
                        .setTitle(R.string.add_product_count)
                        .setNegativeButton(context.getString(R.string.input_amount_cancel), this)
                        .setPositiveButton(context.getString(R.string.input_amount_sure), this)
                        .create();
                mInputAmountDialog.show();

            } else if (id == R.id.relativelayout_name_container) {
//                Context context = v.getContext();
//                Intent intent = new Intent(v.getContext(), SaleStatisActivity.class);
//                intent.putExtra(SaleStatisActivity.FID, mFoodHourBean.getFid());
//                intent.putExtra(SaleStatisActivity.CHART_TYPE, SaleStatisActivity.FOOD_PREHOUR);
//                intent.putExtra(SaleStatisActivity.SEARCH_WEEKDAY, 1);
//
//                context.startActivity(intent);
            }
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            mInputAmountDialog.dismiss();
            mInputAmountDialog = null;
            if (which == DialogInterface.BUTTON_NEGATIVE) {


            } else if (which == DialogInterface.BUTTON_POSITIVE) {
                int inputAmount = Integer.parseInt(editTextInputAmount.getText().toString());
                if(inputAmount <=0){
                    Toast.makeText(MainApp.getInstances(), R.string.no_product_tips, Toast.LENGTH_LONG).show();
                    return;
                }

                Integer productCount = mDailyProductBean.getProductCount();
                mDailyProductBean.setProductCount(productCount + inputAmount );
                new DailyProductDao().insertOrUpdataDailyProduct(mDailyProductBean);

                new AddDailyProductActivity$$ObjectNoticeInter().updataTodayDailyProduction();

            }
        }
    }
}
