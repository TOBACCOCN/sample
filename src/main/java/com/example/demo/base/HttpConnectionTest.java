package com.example.demo.base;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

public class HttpConnectionTest {

	public static void main(String[] args) {
		try {
			HttpsURLConnection.setDefaultHostnameVerifier(new NullHostNameVerifier());
			URL url = new URL("https://180.101.147.89:8743/iocm/app/sub/v1.2.0/subscriptions?"
					+ "appId=b40503mHulCdjZh5YUe_fBRAA1Qa&notifyType=deviceDataChanged&pageNo=0&pageSize=10");
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(null, tm, /*new java.security.SecureRandom()*/null);
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			
			conn.setSSLSocketFactory(ssf);
//			conn.connect();
			conn.setReadTimeout(10 * 1000);
			conn.setDoInput(true);// 允许输入
			conn.setDoOutput(true);// 允许输出
			conn.setUseCaches(false); // 不允许使用缓存
			conn.setRequestMethod("GET");
			conn.setRequestProperty("connection", "keep-alive");
			conn.setRequestProperty("charset", "UTF-8");
			conn.setRequestProperty("app_key", "b40503mHulCdjZh5YUe_fBRAA1Qa");
			conn.setRequestProperty("Authorization", "Bearer e8cd9da6a62ebcb3fc70c361a08f987a");
			
			InputStream in = null;
			
			int res = conn.getResponseCode();
			System.out.println(res);
	        if (res == 200) {
		        in = conn.getInputStream();
		        ByteArrayOutputStream baos = new ByteArrayOutputStream();
		        int len;
		        byte[] buf = new byte[8192];
		        while ((len = in.read()) != -1) {
		        	baos.write(buf, 0, len);
		        }
		        System.out.println(baos.toString("UTF-8"));
	        }
	        conn.disconnect();
	    
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
