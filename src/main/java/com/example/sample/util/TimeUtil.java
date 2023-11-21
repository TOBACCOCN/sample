package com.example.sample.util;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
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
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
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
    public static LocalDateTime getUtcTime() {
        // return date2LocalDateTime(Date.from(Clock.systemUTC().instant()));

        return local2Utc(LocalDateTime.now());
    }

    /**
     * 将本地时区时间转换为 UTC 时间
     *
     * @param localDateTime: 本地时间
     * @return java.time.LocalDateTime
     */
    public static LocalDateTime local2Utc(LocalDateTime localDateTime) {
        return LocalDateTime.ofInstant(localDateTime.atZone(ZoneId.systemDefault()).toInstant(), ZoneId.of("UTC"));
    }

    /**
     * 将 UTC 时间转换为本地时区时间
     *
     * @param utcDateTime: UTC 时间
     * @return java.time.LocalDateTime
     */
    public static LocalDateTime utc2Local(LocalDateTime utcDateTime) {
        return LocalDateTime.ofInstant(utcDateTime.atZone(ZoneId.of("UTC")).toInstant(), ZoneId.systemDefault());
    }

    /**
     * 将 UTC 时间按经度转换到对应时区时间
     *
     * @param utcDateTime: UTC 时间
     * @param longitude: 经度（东经为正，西经为负）
     * @return java.time.LocalDateTime
     */
    public static LocalDateTime utc2Other(LocalDateTime utcDateTime, double longitude) throws Exception {
        int hours = getZoneOffsetHours(longitude);
        if (hours == 0) {
            return utcDateTime;
        }
        ZoneOffset zoneOffset = ZoneOffset.ofHours(hours);
        return LocalDateTime.ofInstant(utcDateTime.atZone(ZoneId.of("UTC")).toInstant(), zoneOffset);
    }

    /**
     * 根据经度查询与 UTC 时区间隔小时数
     *
     * @param longitude: 经度
     * @return int
     */
    public static int getZoneOffsetHours(double longitude) throws Exception {
        if (longitude > 180 || longitude < -180) {
            throw new Exception("Illegal value, longitude should be <= 180, and >= -180");
        }
        int offset = (int) (Math.round(longitude) / 15);
        double mod = longitude * 2 % 30;
        if (mod > 15) {
            offset += 1;
        } else if (mod < -15) {
            offset -= 1;
        }
        return offset;
    }

}