package com.example.demo.base;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class POITest {
	
	public static void main(String[] args) {
        HSSFWorkbook workbook = new HSSFWorkbook(), md5workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("table"), md5sheet = md5workbook.createSheet("table");
       
        HSSFRow headRow = sheet.createRow(0), md5headRow = md5sheet.createRow(0);
        headRow.createCell(0).setCellValue("序列号");
        headRow.createCell(1).setCellValue("IMEI码");
        headRow.createCell(2).setCellValue("蓝牙Mac地址");
        headRow.createCell(3).setCellValue("wifi的Mac地址");
        headRow.createCell(4).setCellValue("所属项目");
        headRow.createCell(5).setCellValue("经销商");
        md5headRow.createCell(0).setCellValue("设备编号");
        md5headRow.createCell(1).setCellValue("md5值");
        
        String btMac = "00:00:00:00:00:00", wifiMac = "00:00:00:00:00:00";
        for (int i = 1; i <= 50000; i++) {
        	HSSFRow dataRow = sheet.createRow(i), md5dataRow = md5sheet.createRow(i);
        	String sn = 10000000 + i + "";
        	dataRow.createCell(0).setCellValue(sn);
        	dataRow.createCell(1).setCellValue("");
        	dataRow.createCell(2).setCellValue(btMac);
        	dataRow.createCell(3).setCellValue(wifiMac);
        	dataRow.createCell(4).setCellValue("ET909-V001");
        	dataRow.createCell(5).setCellValue("test1");
        	md5dataRow.createCell(0).setCellValue(sn);
        	md5dataRow.createCell(1).setCellValue(stringMd5(sn + wifiMac + btMac));
        }
        
        try {
			workbook.write(new File("D:/50000翻译机.xls"));
			workbook.close();
			md5workbook.write(new File("D:/md5.xls"));
			md5workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
    public static String stringMd5(String input) {
        try {
            // 拿到一个MD5转换器（如果想要SHA1加密参数换成"SHA1"）
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            // 输入的字符串转换成字节数组
            byte[] inputByteArray = input.getBytes();
            // inputByteArray是输入字符串转换得到的字节数组
            messageDigest.update(inputByteArray);
            // 转换并返回结果，也是字节数组，包含16个元素
            byte[] resultByteArray = messageDigest.digest();
            // 字符数组转换成字符串返回
            return byteArrayToHex(resultByteArray);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
    
    public static String byteArrayToHex(byte[] byteArray) {
        // 首先初始化一个字符数组，用来存放每个16进制字符
        char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        // new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符）
        char[] resultCharArray = new char[byteArray.length * 2];
        // 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
        int index = 0;
        for (byte b : byteArray) {
            resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b & 0xf];
        }
        // 字符数组组合成字符串返回
        return new String(resultCharArray);
    }

}
