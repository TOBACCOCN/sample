package com.example.sample.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
public class TimeUtilTest {

    @Test
    public void getZoneOffset() {
        int zoneOffset = TimeUtil.getZoneOffset(7.5);
        log.info(">>>>> zoneOffset: [{}]", zoneOffset);
        Assert.assertTrue("zoneOffset 应处于 -12 到 12 之间", zoneOffset >= -12 && zoneOffset <= 12);
    }

    @Test
    public void getUtcTime() throws Exception {
        String utcTime = TimeUtil.format(TimeUtil.date2LocalDateTime(TimeUtil.getUtcTime()), "yyyy-MM-dd HH:mm:ss");
        // String utcTime = TimeUtil.format(Calendar.getInstance().getTime(), "yyyy-MM-dd HH:mm:ss");
        log.info(">>>>> utcTime: [{}]", utcTime);
        Assert.assertNotNull("utcTime 不应为空", utcTime);
        String utc = Clock.systemUTC().instant().toString();
        log.info(">>>>> utc: [{}]", utc);
    }

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
}