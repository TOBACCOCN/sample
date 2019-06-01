package com.example.demo.base;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.Logger;

public class ErrorPrintUtil {
	
	public static void printErrorMsg(Logger logger, Throwable e, String prefix) {
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter, true));
        if (prefix != null) {
        	logger.error(prefix + stringWriter.toString());
        } else {
        	logger.error(stringWriter.toString());
        }
	}

}
