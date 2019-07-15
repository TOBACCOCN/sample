package com.example.sample.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HourMinuteAngle {

    private static Logger logger = LoggerFactory.getLogger(HourMinuteAngle.class);

    public static void main(String[] args) {
        int hour = 7;
        int minute = 15;
        double hourHandAnglePerHour = 360.0 / 12;        // 时针每过一小时所处位置相对 12 点位置增加的角度
        double hourHandAnglePerMinute = 360.0 / (12 * 60);        // 时针每过一分钟所处位置相对 12 点位置增加的角度
        double minuteHandAnglePerMinute = 360.0 / 60;        // 分针每过一分钟所处位置相对 12 点位置增加的角度
        double angle = Math.abs(minute * minuteHandAnglePerMinute - hour * hourHandAnglePerHour - minute * hourHandAnglePerMinute);
        logger.info(">>>>> ANGLE BETWEEN HOUR HAND AND MINUTE HAND OF [{}:{}] IS [{}]", hour, minute, angle <= 180 ? angle : (360 - angle));
    }

}
