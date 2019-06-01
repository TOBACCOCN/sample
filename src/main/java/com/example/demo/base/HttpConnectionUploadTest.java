package com.example.demo.base;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpConnectionUploadTest {

	public static void main(String[] args) {
//		doUpload();
		for (int i = 0; i < 20; i++) {
			new Thread(() -> {
				doUpload();
			}).start();
		}
	}

	public static void doUpload() {
		try {
			URL url = new URL("http://localhost:8762/upload");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setReadTimeout(10 * 1000);
			conn.setDoInput(true);// 允许输入
			conn.setDoOutput(true);// 允许输出
			conn.setUseCaches(false); // 不允许使用缓存
			conn.setRequestMethod("POST");
			conn.setRequestProperty("connection", "keep-alive");
			conn.setRequestProperty("charset", "UTF-8");
			conn.setRequestProperty("Content-Type", "application/octet-stream");
			File file = new File("D:\\download\\Xshell-6.0.0095_yy.exe");
	        DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
	        InputStream is = new FileInputStream(file);
	        byte[] buffer = new byte[8192];
	        int len = 0;
	        while ((len = is.read(buffer)) != -1) {
	        	outStream.write(buffer, 0, len);
	        }
	        is.close();

			InputStream in = null;

			int res = conn.getResponseCode();
			System.out.println(res);
			if (res == 200) {
		        in = conn.getInputStream();
		        ByteArrayOutputStream baos = new ByteArrayOutputStream();
		        int length;
		        byte[] buf = new byte[8192];
		        while ((length = in.read()) != -1) {
		        	baos.write(buf, 0, length);
		        }
		        System.out.println(baos.toString("UTF-8"));
			}
			conn.disconnect();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
