package com.keven.kubi.activity.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.keven.kubi.R;
import com.keven.kubi.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by keven-liang on 2018/5/12.
 */

public class SaleStatisAdapter extends RecyclerView.Adapter {

    private LayoutInflater layoutInflater;
    private List<LineData> lineDataList = new ArrayList<>();
    private OnChartClickListener mOnChartClickListener;

    public SaleStatisAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }

    private int type ;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = layoutInflater.inflate(R.layout.statis_item_layout, parent, false);

        return new SaleStatisHolder(inflate);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        LineData lineData = lineDataList.get(position);
        ((SaleStatisHolder)holder).setType(type);
        ((SaleStatisHolder)holder).setLineData(lineData);
        ((SaleStatisHolder)holder).setOnChartClickListener(mOnChartClickListener);
    }

    @Override
    public int getItemCount() {
        return lineDataList.size();
    }

    public void setOnChartClickListener(OnChartClickListener onChartClickListener) {
        this.mOnChartClickListener = onChartClickListener;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setEntryList(List<Pair<String ,List<Entry>>> labelEntryList) {
        lineDataList.clear();
        for (Pair<String ,List<Entry>> labelEntry: labelEntryList) {

            String label = labelEntry.first;
            List<Entry> entryList = labelEntry.second;

                // create a dataset and give it a type
                LineDataSet dataSet = new LineDataSet(entryList, label);

                // set the line to be drawn like this "- - - - - -"
                dataSet.enableDashedLine(10f, 5f, 0f);
                dataSet.enableDashedHighlightLine(10f, 5f, 0f);
                dataSet.setColor(Color.BLACK);
                dataSet.setCircleColor(Color.BLACK);
                dataSet.setLineWidth(1f);
                dataSet.setCircleRadius(3f);
                dataSet.setDrawCircleHole(false);
                dataSet.setValueTextSize(9f);
                dataSet.setDrawFilled(true);

                LineData lineData = new LineData(dataSet);

                lineDataList.add(lineData);

        }

        notifyDataSetChanged();

    }

    public static class SaleStatisHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener,IAxisValueFormatter {

        @BindView(R.id.linechart_sale_statis)
        LineChart lineChart;
        @BindView(R.id.framelayout_chart_container)
        FrameLayout framelayoutChartContainer;

        private OnChartClickListener mOnChartClickListener;
        private LineData lineData;
        private int type = 1; //1:日销量  2:时销量

        public SaleStatisHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            lineChart.setOnLongClickListener(this);
            lineChart.getXAxis().setValueFormatter(this);
        }

        public void setLineData(LineData lineData){
            this.lineData = lineData;
            lineChart.setData(lineData);
            lineChart.postInvalidate();

        }

        public void setOnChartClickListener(OnChartClickListener onChartClickListener) {
            this.mOnChartClickListener = onChartClickListener;
        }

        public void setType(int type) {
            this.type = type;
        }

        @Override
        public boolean onLongClick(View v) {
            if(v.getId() == R.id.linechart_sale_statis) {
                if (mOnChartClickListener != null) {
                    mOnChartClickListener.onChartClickedListener(lineData);
                }
            }
            return false;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            int startHour = TimeUtils.START_HOUR;
            String s = "";
            if(type == 1){
                int floor = (int) Math.floor(value);
                if(Float.compare(floor, value) == 0) {
                    s = "周" + (value+1 );
                }

            }else if(type == 2 ) {

                int floor = (int) Math.floor(value);

                if(Float.compare(floor, value) == 0) {
                    s = (floor ) + ":00 -- " + (floor +1) + ":00";
                }

            }

            return s;

        }
    }

    public interface OnChartClickListener{
        void onChartClickedListener(LineData lineData);
    }
}
