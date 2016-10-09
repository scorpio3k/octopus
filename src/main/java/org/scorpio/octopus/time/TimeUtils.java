package org.scorpio.octopus.time;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtils {

    private  static final Calendar CALENDAR = Calendar.getInstance();

    public static final SimpleDateFormat SHORTDATEFORMAT = new SimpleDateFormat("yyyyMMdd");
    public static final SimpleDateFormat SHORT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat LONG_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat HMS_FORMAT = new SimpleDateFormat("HH:mm:ss");
    public static final SimpleDateFormat formatTimestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 获取当前日期的毫秒数
     */
    public static long nowTimeMillis(){
        return System.currentTimeMillis();
    }

    /**
     * 获取当前日期：yyyy-MM-dd
     */
    public static String getDateString(){
        return SHORT_DATE_FORMAT.format(new Date());
    }

    /**
     * 获取日期：yyyy-MM-dd
     * @param date
     */
    public static String getDateString(Date date){
        return SHORT_DATE_FORMAT.format(date);
    }

    /**
     * 时间：HH:mm:ss
     */
    public static String getTime(){
        return HMS_FORMAT.format(new Date());
    }


    /**
     * 返回年份
     */
    public static  int getYear(Date date){
        CALENDAR.setTime(date);
        return CALENDAR.get(Calendar.YEAR);
    }
}
