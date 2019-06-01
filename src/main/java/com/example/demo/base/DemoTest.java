package com.example.demo.base;

import com.example.demo.upload.Md5Utils;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.Clock;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class DemoTest {

	public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
//		List<String> list = Files.readAllLines(Paths.get("C:\\Users\\Administrator\\Desktop\\demo.txt"), StandardCharsets.UTF_8);
//		for (int i = 0; i < list.size(); i++) {
//			String  path = "C:\\Users\\Administrator\\Desktop\\demo\\" + i + ".txt";
//			File file = new File(path);
//			if (!file.exists()) {
//				file.createNewFile();
//			}
//			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
//			bw.write(list.get(i));
//			bw.close();
//		}
		List<String> a = new ArrayList<>();
		System.out.println("asffasdfasfdasdfadsfs".hashCode());
		System.out.println("asffasdfaasdfadsfs".hashCode());
		System.out.println("asffasdfaasdfadsfs".hashCode());
		System.out.println("asffasasfdasdfadsfs".hashCode());
		a.add("aa");
		a.add("bb");
		a.add("cc");
		a.add("dd");
		System.out.println(a);
		List<String> b = new ArrayList<>();
		b.add("bb");
		b.add("cc");
		b.add("ee");
		b.add("ff");
		System.out.println(b);
		a.retainAll(b);
		System.out.println(a);
		System.out.println(b);
		System.out.println("a.b".lastIndexOf("."));
		System.out.println(System.getProperty("line.separator") + "aa");
		File audioDir = new File("D:\\download\\中文英文语音数据\\中文30小时\\wav\\train\\S0003");
		File[] audioFiles = audioDir.listFiles();
		for (File file : audioFiles) {
			System.out.println(Md5Utils.md5ForFile(file) + "=" + file.getPath());
		}

		String s = "新年快乐！";
		char[] chars = s.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			System.out.println(" " + chars[i] + " " + (int) chars[i]);
		}
		System.out.println(-1L ^ (-1L << 5));
		System.out.println(new AtomicLong(System.currentTimeMillis()).get());
		System.out.println(UUID.randomUUID().toString().replaceAll("-", ""));
//        System.out.println("integer of null: "+Integer.parseInt(null));
//        List<String> list = Files.readAllLines(Paths.get("C:\\Users\\Administrator\\Desktop\\new"), StandardCharsets.UTF_8);
//        list.stream().forEach(e -> System.out.print(e + " "));
//        FileOutputStream fos = new FileOutputStream(new File("D://ac.txt"));
//        fos.write("hello".getBytes());
//        fos.close();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(Clock.systemUTC().millis()));
		System.out.println(calendar.toString());
		System.out.println(calendar.get(Calendar.MONTH));
		System.out.println(calendar.get(Calendar.DATE));
		System.out.println(calendar.get(Calendar.DAY_OF_MONTH));
		System.out.println(Integer.parseInt("01"));
		System.out.println(Math.pow(10, 15));
		System.out.println(Arrays.toString(new String[] {"a", "b"}));
	}

}
