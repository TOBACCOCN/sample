package com.example.demo.base;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/**
 * 证书信任管理器实现
 * @author zhangyonghong
 * @date 2018.3.14
 *
 */
public class MyX509TrustManager implements X509TrustManager {
	
	public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
	
	public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
	
	public X509Certificate[] getAcceptedIssuers() {
		return null;
	}
	
}
