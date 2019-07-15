package com.example.sample.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * UTC 时间工具类
 *
 * @author zhangyonghong
 * @date 2018.3.29
 */
public final class TimeUtil {

    private static Logger logger = LoggerFactory.getLogger(TimeUtil.class);

    /**
     * yyyyMMdd'T'HHmmss'Z'格式时间转日期Date
     *
     * @return
     */
    public static String date2TzTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
        return format.format(date);
    }

    /**
     * yyyyMMdd'T'HHmmss'Z'格式时间转日期Date
     */
    public static Date tzTime2Date(String tzTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
        try {
            return format.parse(tzTime);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * yyyyMMdd'T'HHmmss'Z'格式时间转yyyy-MM-dd HH:mm:ss格式
     */
    public static String tzTime2Pretty(String tzTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
        SimpleDateFormat prettyFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return prettyFormat.format(format.parse(tzTime));
        } catch (ParseException e) {
            return "";
        }
    }

    /**
     * 将UTC时间按经度转换到时间
     */
    public static String utc2Other(String utcTime, int longitude) {
        int zoneOffset = getZoneOffset(longitude);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
        SimpleDateFormat prettyFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            calendar.setTime(format.parse(utcTime));
            calendar.add(Calendar.HOUR, zoneOffset);
            return prettyFormat.format(calendar.getTime());
        } catch (ParseException e) {
            logger.info(e.getMessage());
            return "";
        }
    }

    /**
     * 根据经度查询与UTC时区间隔时区数
     */
    public static int getZoneOffset(int longitude) {
        int zone = longitude / 15;
        boolean plus = longitude * 2 % 30 > 15;
        boolean minus = longitude * 2 % 30 < -15;
        if (plus) {
            zone += 1;
        } else if (minus) {
            zone -= 1;
        }
        return zone;
    }

    /**
     * 获取UTC时间
     */
    public static String getUtcTime() {
        String localTime = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'").format(new Date());
        try {
            return local2Utc(localTime);
        } catch (ParseException e) {
            return "";
        }
    }

    /**
     * 将本地时区时间转换为UTC时间
     */
    public static String local2Utc(String localTime) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
        format.setTimeZone(TimeZone.getDefault());
        Date localDate = format.parse(localTime);
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        return format.format(localDate);
    }

    /**
     * 将UTC时间转换为本地时区时间
     */
    public static String utc2Local(String utcTime) throws ParseException {
        SimpleDateFormat utcFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
        utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date utcDate = utcFormat.parse(utcTime);
        SimpleDateFormat localFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
        localFormat.setTimeZone(TimeZone.getDefault());
        return localFormat.format(utcDate);
    }


    public static void main(String[] args) throws ParseException {
        System.out.println(local2Utc("20180329T203728Z"));
        System.out.println(utc2Local("20180329T203728Z"));
    }

}