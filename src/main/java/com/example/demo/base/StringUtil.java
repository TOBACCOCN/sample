package com.example.demo.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.UUID;

public class StringUtil {

    private static Logger logger = LoggerFactory.getLogger(StringUtil.class);

    public static String[] splitString(String src, String splitor) {
        StringTokenizer s = new StringTokenizer(src, splitor);

        String[] array = new String[s.countTokens()];
        int i = 0;
        while (s.hasMoreTokens()) {
            array[(i++)] = s.nextToken();
        }
        return array;
    }

    public static void main(String[] args) {
        logger.info(Arrays.toString(splitString(UUID.randomUUID().toString(), "-")));
    }
}
