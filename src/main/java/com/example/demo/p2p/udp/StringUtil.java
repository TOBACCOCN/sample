package com.example.demo.p2p.udp;

import java.util.StringTokenizer;

public class StringUtil {
    public static String[] splitString(String src, String splitor) {
        StringTokenizer s = new StringTokenizer(src, splitor);

        String[] strs = new String[s.countTokens()];
        int i = 0;
        while (s.hasMoreTokens()) {
            strs[(i++)] = s.nextToken();
        }
        return strs;
    }
}
