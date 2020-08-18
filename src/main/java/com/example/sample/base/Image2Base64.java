package com.example.sample.base;

import java.io.IOException;
import java.util.Base64;

/**
 * 将图片进行 Base64 编码/Base64 串解码出图片
 *
 * @author zhangyonghong
 * @date 2020.5.5
 */
public class Image2Base64 {

    /**
     * 将图片进行 Base64 编码
     *
     * @param imageBytes 图片字节数组
     * @return Base64 编码串
     */
    public static String image2Base64(byte[] imageBytes) {
        return Base64.getEncoder().encodeToString(imageBytes);
    }

    /**
     * Base64 串解码出图片
     *
     * @param base64Encoded Base64 串
     * @return 图片字节数组
     */
    public static byte[] base64ToImage(String base64Encoded) {
        return Base64.getDecoder().decode(base64Encoded);
    }

    public static void main(String[] args) throws IOException {
        // String imagePath = "D:\\test.jpg";
        // FileInputStream fis = new FileInputStream(imagePath);
        // ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // byte[] buf = new byte[1024];
        // int len;
        // while ((len = fis.read(buf)) > 0) {
        //     baos.write(buf, 0, len);
        // }
        // fis.close();
        // String base64 = image2Base64(baos.toByteArray());
        // System.out.println(base64);
        // System.out.println(base64.length());
        //
        // byte[] bytes = base64ToImage(base64);
        // ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        // FileOutputStream fos = new FileOutputStream("D:/output.jpg");
        // while ((len = bais.read(buf)) > 0) {
        //     fos.write(buf, 0, len);
        // }
        // fos.close();
    }

}
