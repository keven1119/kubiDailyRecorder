package com.keven.kubi.activity.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.StringRes;
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
import com.keven.kubi.activity.SaleStatisActivity;
import com.keven.kubi.bean.DailyProductBean;
import com.keven.kubi.bean.FoodHourBean;
import com.keven.kubi.bean.FoodProductionBean;
import com.keven.kubi.dao.DailyProductDao;
import com.keven.kubi.dao.FoodSaleDao;
import com.keven.kubi.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by keven-liang on 2018/4/28.
 */

public class WriteStatisAdapter extends RecyclerView.Adapter {

    private LayoutInflater layoutInflater;
    private List<FoodHourBean> mFoodHourBeanList = new ArrayList<>();
    private OnCountInputRecordListener mOnCountInputRecordListener;

    public WriteStatisAdapter(Context context){
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = layoutInflater.inflate(R.layout.quantity_statis_item_layout, parent, false);
        return new WriteStatisHolder(inflate);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        FoodHourBean foodHourBean = mFoodHourBeanList.get(position);
        ((WriteStatisHolder)holder).setFoodProduction(foodHourBean);
        ((WriteStatisHolder)holder).setOnCountInputRecordListener(mOnCountInputRecordListener);

    }

    public void setFoodHourBeanList(List<FoodHourBean> mFoodHourBeanList) {
        this.mFoodHourBeanList = mFoodHourBeanList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mFoodHourBeanList.size();
    }



    public void setOnCountInputRecordListener(OnCountInputRecordListener onCountInputRecordListener) {
        this.mOnCountInputRecordListener = onCountInputRecordListener;
    }

    public static class WriteStatisHolder extends RecyclerView.ViewHolder implements View.OnClickListener ,
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

        private FoodHourBean mFoodHourBean;
        private AlertDialog mInputAmountDialog;
        private EditText editTextInputAmount;

        private String clickFillTips = "";
        private String addFillTips = "";
        private int normalColor;

        private OnCountInputRecordListener onCountInputRecordListener;
        private int dialogType = 0; //0:第一次输入产量，  1：第二次输入常量

        public WriteStatisHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            relativelayoutNameContainer.setOnClickListener(this);
            textviewAmountInput.setOnClickListener(this);
            textviewDeleteFood.setOnClickListener(this);
            textviewAmountInput.setText(R.string.click_to_fill);
            normalColor = textviewAmountInput.getCurrentTextColor();
            clickFillTips = textviewAmountInput.getText().toString();
            addFillTips = itemView.getContext().getString(R.string.residue_add);
            textviewDeleteFood.setText("");

        }

        public void setFoodProduction(FoodHourBean foodProduction){
            mFoodHourBean = foodProduction;
            textviewNumber.setText(getAdapterPosition()+"");
            textviewName.setText(foodProduction.getName());
            int hourScaleCount = foodProduction.getHourScaleCount();

            if(hourScaleCount == -1){
                dialogType = 0;
                textviewDeleteFood.setText("");
                textviewDeleteFood.setClickable(false);
                textviewAmountInput.setText(clickFillTips);
                textviewAmountInput.setTextColor(Color.RED);
                textviewAmountInput.setClickable(true);
            }else {
                dialogType = 1;
                textviewDeleteFood.setText(addFillTips);
                textviewDeleteFood.setClickable(true);
                textviewAmountInput.setText(itemView.getContext().getString(R.string.previous_hour_scale_amount,hourScaleCount + ""));
                textviewAmountInput.setTextColor(normalColor);
                textviewAmountInput.setClickable(false);

            }
        }

        public void setOnCountInputRecordListener(OnCountInputRecordListener onCountInputRecordListener) {
            this.onCountInputRecordListener = onCountInputRecordListener;
        }

        @Override
        public void onClick(View v) {
            int id = v.getId();

            if(id == R.id.textview_amount_input){
                dialogType = 0;
                showInputDialog(v.getContext(),R.string.current_production_count);

            }else if(id == R.id.relativelayout_name_container){
                Context context = v.getContext();
                Intent intent = new Intent(v.getContext(), SaleStatisActivity.class);
                intent.putExtra(SaleStatisActivity.FID , mFoodHourBean.getFid());
                intent.putExtra(SaleStatisActivity.CHART_TYPE, SaleStatisActivity.FOOD_PREHOUR);

                context.startActivity(intent);
            }else if(id == R.id.textview_delete_food){
                dialogType = 1;

                showInputDialog(v.getContext(),R.string.current_production_count);
            }
        }

        private void showInputDialog(Context context, @StringRes int textId) {
            if(mInputAmountDialog != null){
                mInputAmountDialog.dismiss();
                mInputAmountDialog = null;
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            View inflate = LayoutInflater.from(context).inflate(R.layout.input_amount_layout,null, false);
            editTextInputAmount = inflate.findViewById(R.id.edittext_input_amount);
            editTextInputAmount.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
            mInputAmountDialog = builder.setCancelable(false)
                    .setView(inflate)
                    .setTitle(textId)
                    .setNegativeButton(context.getString(R.string.input_amount_cancel),this)
                    .setPositiveButton(context.getString(R.string.input_amount_sure),this)
                    .create();
            mInputAmountDialog.show();
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            mInputAmountDialog.dismiss();
            mInputAmountDialog = null;
            if(which == DialogInterface.BUTTON_NEGATIVE){


            }else if(which == DialogInterface.BUTTON_POSITIVE){


                int inputAmount = Integer.parseInt(editTextInputAmount.getText().toString());

                if(onCountInputRecordListener != null){
                    onCountInputRecordListener.OnCountInputRecorded(mFoodHourBean, inputAmount);



                }
            }
        }
    }

    public interface OnCountInputRecordListener{

        /**
         *
         * @param hourBean
         */
        void OnCountInputRecorded(FoodHourBean hourBean, int inputAmount);
    }
}
