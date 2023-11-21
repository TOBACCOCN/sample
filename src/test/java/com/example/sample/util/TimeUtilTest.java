package com.example.sample.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
public class TimeUtilTest {

    @Test
    public void date2LocalDateTime() {
        LocalDateTime localDateTime= TimeUtil.date2LocalDateTime(new Date());
        log.info(">>>>> localDateTime: [{}]", localDateTime);
        Assert.assertNotNull("localDateTime 不应为空", localDateTime);
    }

    @Test
    public void localDateTime2Date() {
        Date date = TimeUtil.localDateTime2Date(LocalDateTime.now());
        log.info(">>>>> date: [{}]", date);
        Assert.assertNotNull("date 不应为空", date);
    }

    @Test
    public void getUtcTime() throws Exception {
        LocalDateTime utcTime = TimeUtil.getUtcTime();
        log.info(">>>>> utcTime: [{}]", utcTime);
        Assert.assertNotNull("utcTime 不应为空", utcTime);
    }

    @Test
    public void local2Utc() {
        LocalDateTime utcDateTime = TimeUtil.local2Utc(LocalDateTime.now());
        log.info(">>>>> utcDateTime: [{}]", utcDateTime);
        Assert.assertNotNull("utcDateTime 不应为空", utcDateTime);
    }

    @Test
    public void utc2Local() {
        LocalDateTime localDateTime = TimeUtil.utc2Local(TimeUtil.local2Utc(LocalDateTime.now().plusHours(2)));
        log.info(">>>>> localDateTime: [{}]", localDateTime);
        Assert.assertNotNull("localDateTime 不应为空", localDateTime);
    }

    @Test
    public void utc2Other() throws Exception {
        LocalDateTime other = TimeUtil.utc2Other(TimeUtil.getUtcTime(), -7.6);
        log.info(">>>>> other: [{}]", other);
    }

    @Test
    public void getZoneOffset() throws Exception {
        int zoneOffsetHours = TimeUtil.getZoneOffsetHours(173);
        log.info(">>>>> zoneOffsetHours: [{}]", zoneOffsetHours);
        Assert.assertTrue("zoneOffsetHours 应处于 -12 到 12 之间", zoneOffsetHours >= -12 && zoneOffsetHours <= 12);
    }
}