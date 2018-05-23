package com.keven.kubi.utils;

import android.content.Context;
import android.content.Intent;
import android.provider.AlarmClock;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by keven-liang on 2018/5/3.
 */

public class TimeUtils {

    public static final int START_HOUR = 6;
    public static final int END_HOUR = 20;

    /**
     * 获取前一个整点的毫秒数
     * @return 单位：毫秒
     */
    public static long getPervoiusHourMillSceond(){
        long l = System.currentTimeMillis() / 1000;
        return (l/(60*60) -1 ) *3600 * 1000;
    }

    /**
     * 获取该点整点的毫秒数
     * @return 单位：毫秒
     */
    public static long getThisHourMillSceond(){
        long l = System.currentTimeMillis() / 1000;
        return (l/(60*60)  ) *3600 * 1000;
    }

    /**
     * 获取下一个整点的毫秒数
     * @return 单位：毫秒
     */
    public static long getNextHourMillSecond(){
        long l = System.currentTimeMillis() / 1000;
        return (l/(60*60) + 1 ) *3600 * 1000;
    }


    /**
     * 当天零点是的毫秒数
     * @return 单位：毫秒
     */
    public static long getTodayMillSceond(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date zero = calendar.getTime();
        long time = zero.getTime();
        long l = time / 1000 * 1000;

        return l;
    }


    /**
     * 当天最后一秒是的毫秒数
     * @return 单位：毫秒
     */
    public static long getTodayLastMillSceond(){
        long l = System.currentTimeMillis() / 1000;
        return getTodayMillSceond()  + 23 * 3600 * 1000 + 59 * 60* 1000  ;
    }

    /**
     * 本周周一0时0分的毫秒数
     * @return 单位：毫秒
     */
    public static long getThisWeekStartMillSceond(){
       return getPreviousWeekStartMillSceond(0);
    }

    /**
     * 获取上 last本周周一0时0分的毫秒数
     * @param last 1:上一周 2：上两周
     * @return 单位：毫秒
     */
    public static long getPreviousWeekStartMillSceond(int last){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        long millTime = cal.getTime().getTime();
        millTime = millTime/1000 * 1000;
        return millTime - last*7*24*3600*1000;
    }

    /**
     * 判断今天日期是本周的第几天,  以周一开始 1，2，3，4，5，6，7
     */
    public static int getTodayWeekDay(){
        Calendar cal = Calendar.getInstance();

        int dayCout = cal.get(Calendar.DAY_OF_WEEK) - 1;

        if(dayCout == 0){
            //周日
            dayCout = 7;
        }
        return dayCout;
    }

    /**
     * 判断今天日期是本周的第几天,  以周一开始 1，2，3，4，5，6，7
     */
    public static int getWeekDayByTimeStamp(long timeStamp){
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeStamp);

        int dayCout = cal.get(Calendar.DAY_OF_WEEK) - 1;

        if(dayCout == 0){
            //周日
            dayCout = 7;
        }
        return dayCout;
    }


    /**
     * 获取本周每天的时间戳
     * 1，2，3，4，5，6，7
     */
    public static long getTodayWeekDayTimeStamp(int weekday){

        long thisWeekStartMillSceond = getThisWeekStartMillSceond();

        return thisWeekStartMillSceond + (weekday - 1) * 24* 3600 * 1000;

    }




    /**
     * 判断时间是否在时间段内
     * @param nowTime
     * @param beginTime
     * @param endTime
     * @return
     */
    public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 设置闹钟, 会推迟1分钟响
     * @param alarmTimeStamp 单位：毫秒
     */
    public static void setSystemAlarm(Context context, long alarmTimeStamp){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(alarmTimeStamp));
//setAlarm(calendar);
        Intent alarmIntent = new Intent(AlarmClock.ACTION_SET_ALARM);
        alarmIntent.putExtra(AlarmClock.EXTRA_MESSAGE, "请填写时销售信息");
        alarmIntent.putExtra(AlarmClock.EXTRA_HOUR, calendar.get(Calendar.HOUR_OF_DAY));
        alarmIntent.putExtra(AlarmClock.EXTRA_MINUTES, calendar.get(Calendar.MINUTE) + 1);
        alarmIntent.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
        alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(alarmIntent);
    }

}
