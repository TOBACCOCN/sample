package com.example.sample.netty.http;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.io.FileInputStream;
import java.security.KeyStore;

public class SSLContextFactory {

	public static SSLContext getSslContext() throws Exception {
		char[] passArray = "hsc123".toCharArray();
		// char[] passArray = "E9dFHK4O".toCharArray();
		SSLContext sslContext = SSLContext.getInstance("TLSv1");
		KeyStore ks = KeyStore.getInstance("JKS");
		// 加载keytool 生成的文件
		FileInputStream inputStream = new FileInputStream("C:\\Users\\Administrator\\local.jks");
		// FileInputStream inputStream = new FileInputStream("/home/trans1127/env/cert/1915332_58.eliteei.com.pfx");
		ks.load(inputStream, passArray);
		KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
		kmf.init(ks, passArray);
		sslContext.init(kmf.getKeyManagers(), null, null);
		inputStream.close();
		return sslContext;

	}

}
