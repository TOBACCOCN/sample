package com.example.sample.util;

import java.io.*;

public class IOUtil {

    public static byte[] stream2Bytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[8192];
        int len;
        while ((len = inputStream.read(buf)) != -1) {
            baos.write(buf, 0, len);
        }
        return baos.toByteArray();
    }

    /**
     * 将字节数组写到文件
     *
     * @param bytes    字节数组
     * @param filePath 文件路径
     */
    public static void write2File(byte[] bytes, String filePath) throws Exception {
        File file = new File(filePath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        FileOutputStream fos = new FileOutputStream(new File(filePath));
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        int len;
        byte[] buf = new byte[8192];
        while ((len = bais.read(buf)) != -1) {
            fos.write(buf, 0, len);
        }
        bais.close();
        fos.close();
    }

    /**
     * 从文件获取其内容的字节数组
     *
     * @param filePath 文件路径
     * @return 文件内容的字节数组
     */
    public static byte[] file2Bytes(String filePath) throws IOException {
        InputStream inputStream = new FileInputStream(filePath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int len;
        byte[] buf = new byte[8192];
        while ((len = inputStream.read(buf)) != -1) {
            baos.write(buf, 0, len);
        }
        byte[] bytes = baos.toByteArray();
        inputStream.close();
        return bytes;
    }

}
