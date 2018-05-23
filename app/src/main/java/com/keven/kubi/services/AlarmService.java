package com.keven.kubi.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.keven.kubi.MainApp;
import com.keven.kubi.bean.HourlyAlarmBean;
import com.keven.kubi.db.greendao.HourlyAlarmBeanDao;
import com.keven.kubi.utils.AppUtils;
import com.keven.kubi.utils.LogUtils;
import com.keven.kubi.utils.TimeUtils;

import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.query.QueryBuilder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by keven-liang on 2018/5/3.
 */

public class AlarmService implements Runnable {

    private final static String TAG = AlarmService.class.getSimpleName();
    private Timer mTimer;
    private HourlyAlarmBeanDao mHourlyAlarmBeanDao;

    @Override
    public void run() {
        mHourlyAlarmBeanDao = MainApp.getInstances().getDaoSession().getHourlyAlarmBeanDao();

        mHourlyAlarmBeanDao.deleteAll();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        Date beginTime = null;
        Date endTime = null;
        try {
            beginTime = df.parse("0" + TimeUtils.START_HOUR +":00");
            endTime = df.parse(TimeUtils.END_HOUR + ":00");

            long nextHourMillSecond = TimeUtils.getNextHourMillSecond();

            int i = 0;


            while (i < (24)){

                nextHourMillSecond = nextHourMillSecond +  3600 * 1000;

                String format = df.format(new Date(nextHourMillSecond));
                LogUtils.d(TAG, "format ==> "+ format);

                boolean isFit = TimeUtils.belongCalendar(df.parse(df.format(new Date(nextHourMillSecond))), beginTime, endTime);
                if(isFit) {

                    HourlyAlarmBean hourlyAlarmBean = new HourlyAlarmBean();
                    hourlyAlarmBean.setHourTimeStamp(nextHourMillSecond);
                    hourlyAlarmBean.setIsRinged(false);

                    mHourlyAlarmBeanDao.insertOrReplace(hourlyAlarmBean);

                    TimeUtils.setSystemAlarm(MainApp.getInstances(),nextHourMillSecond);

                }

                Thread.sleep(1000);
                i++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public class AlarmTimerTask extends TimerTask{

        @Override
        public void run() {

            Calendar cal=Calendar.getInstance();
            cal.setTime(new Date());
            int mi=cal.get(Calendar.MINUTE);

//            if(mi == 0 || mi == 60){
//                //整点，报闹钟，提醒写入时销量信息
//                String topActivity = AppUtils.getTopActivity(MainApp.getInstances());
//
//                if(!topActivity.contains("com.keven.kubi.activity.WriteStatisActivity")){
//                    TimeUtils.setSystemAlarm(MainApp.getInstances(),System.currentTimeMillis());
//                }
//            }
        }
    }
}
