package com.example.sample.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class Enter2blank {

    private static Logger logger = LoggerFactory.getLogger(Enter2blank.class);

    public static void main(String[] args) throws IOException {
        doEnter2blank("C:\\Users\\Administrator\\Desktop\\demo.txt", "C:\\Users\\Administrator\\Desktop\\zyh.txt");
        logger.info(">>>>> DONE");
    }

    private static void doEnter2blank(String srcPath, String destPath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(srcPath));
        String line;
        StringBuilder builder = new StringBuilder();
        while ((line = br.readLine()) != null) {
            builder.append(line).append(" ");
        }
        br.close();

        BufferedWriter writer = new BufferedWriter(new FileWriter(destPath));
        String output = builder.substring(0, builder.length() - 1);
        writer.write(output);
        writer.close();
    }

}
