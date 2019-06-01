package com.example.demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class ErrorPrintUtil {

    private static Logger logger = LoggerFactory.getLogger(ErrorPrintUtil.class);

    public static void printErrorMsg(Logger logger, Throwable e) {
        printErrorMsg(logger, e, null);
    }

    public static void printErrorMsg(Logger logger, Throwable e, String prefix) {
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter, true));
        if (prefix != null) {
            logger.error(prefix + stringWriter.toString());
        } else {
            logger.error(stringWriter.toString());
        }
    }

    public static void main(String[] args) {
        try {
            FileInputStream fis  = new FileInputStream(new File(""));
            fis.close();
        } catch (Exception e) {
            printErrorMsg(logger, e);
        }
    }

}
