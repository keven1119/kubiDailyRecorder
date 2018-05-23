package com.keven.kubi.utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by keven-liang on 2018/5/11.
 */
public class TimeUtilsTest {

    @Test
    public void testTodayMillsecond(){
        System.out.print("TodayMillsecond ==>" + TimeUtils.getTodayMillSceond());
    }


    @Test
    public void testNextHourMillsecond(){
        System.out.print("NextHourMillsecon==>" + TimeUtils.getNextHourMillSecond());
    }

    @Test
    public void testThisHourMillsecond(){
        System.out.print("ThisHourMillsecond ==> "+TimeUtils.getThisHourMillSceond());
    }

    @Test
    public void testTodayLastMillsecond(){
        System.out.print("TodayLastMillsecond ==> "+TimeUtils.getTodayLastMillSceond());
    }

    @Test
    public void testThisWeekStartMillSceond(){
        System.out.print("ThisWeekStartMillSceond ==> "+TimeUtils.getThisWeekStartMillSceond());

    }

    @Test
    public void testTodayWeekDay(){
        System.out.print("TodayWeekDay ==> "+TimeUtils.getTodayWeekDay());

    }

    @Test
    public void testTodayWeekDayTimeStamp(){
        System.out.print("TodayWeekDayTimeStamp ==> "+TimeUtils.getTodayWeekDayTimeStamp(3));
    }


}