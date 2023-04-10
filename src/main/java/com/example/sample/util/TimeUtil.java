package com.example.sample.util;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 *
 * @author zhangyonghong
 * @date 2018.3.29
 */
@Slf4j
public final class TimeUtil {

    /**
     * Date 转 LocalDateTime
     *
     * @param date: Date 对象
     * @return java.time.LocalDateTime
     */
    public static LocalDateTime date2LocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * LocalDateTime 转 Date
     *
     * @param localDateTime: LocalDateTime 对象
     * @return java.util.Date
     */
    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
        return Date.from(zonedDateTime.toInstant());
    }

    /**
     * 格式化 LocalDateTime
     *
     * @param localDateTime LocalDateTime
     * @param pattern the pattern
     * @return the formatted string of LocalDateTime
     */
    public static String format(LocalDateTime localDateTime, String pattern) throws Exception {
        try {
            return DateTimeFormatter.ofPattern(pattern).format(localDateTime);
        } catch (Exception e) {
            throw new Exception(String.format("[%s] is not valid pattern", pattern));
        }
    }

    /**
     * 获取 UTC 时间
     *
     * @return java.util.Date
     */
    public static Date getUtcTime() {
        Calendar calendar = Calendar.getInstance();
        int zoneOffset = calendar.get(Calendar.ZONE_OFFSET);
        int dstOffset = calendar.get(Calendar.DST_OFFSET);
        calendar.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        return calendar.getTime();
        // return Date.from(Clock.systemUTC().instant());
    }

    /**
     * 将本地时区时间转换为 UTC 时间
     *
     * @param date: 本地时间
     * @return java.util.Date
     */
    public static Date local2Utc(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int zoneOffset = calendar.get(Calendar.ZONE_OFFSET);
        int dstOffset = calendar.get(Calendar.DST_OFFSET);
        calendar.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        return calendar.getTime();
    }

    /**
     * 将 UTC 时间转换为本地时区时间
     *
     * @param utcDate: UTC 时间
     * @return java.util.Date
     */
    public static Date utc2Local(Date utcDate) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(utcDate);
        int zoneOffset = calendar.get(Calendar.ZONE_OFFSET);
        int dstOffset = calendar.get(Calendar.DST_OFFSET);
        calendar.add(Calendar.MILLISECOND, zoneOffset + dstOffset);
        return calendar.getTime();
    }

    /**
     * 将 UTC 时间按经度转换到对应时区时间
     *
     * @param utcDate: UTC 时间
     * @param longitude: 经度（东经为正，西经为负）
     * @return java.util.Date
     */
    public static Date utc2Other(Date utcDate, int longitude) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(utcDate);
        calendar.add(Calendar.HOUR, getZoneOffset(longitude));
        return calendar.getTime();
    }

    /**
     * 根据经度查询与 UTC 时区间隔时区数
     *
     * @param longitude: 经度
     * @return int
     */
    public static int getZoneOffset(double longitude) {
        int zone = (int) (Math.round(longitude) / 15);
        boolean plus = longitude * 2 % 30 > 15;
        boolean minus = longitude * 2 % 30 < -15;
        if (plus) {
            zone += 1;
        } else if (minus) {
            zone -= 1;
        }
        return zone;
    }

}