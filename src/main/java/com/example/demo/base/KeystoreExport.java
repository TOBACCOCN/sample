package com.example.demo.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.security.Key;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.util.Base64;

/**
 * 从keystore文件中导出证书，公钥和私钥
 * @author zhangyonghong
 *
 */
public class KeystoreExport {

	private static String keystorePath = "C:\\Users\\Administrator\\Desktop\\tomcat.keystore";
	private static String keystoreType = KeyStore.getDefaultType();	//other is "JKS"
	private static char[] password = "example".toCharArray();
	private static String alias = "tomcat";
	private static String certPath = "C:\\Users\\Administrator\\Desktop\\cert.cer";
	private static String privatePath = "C:\\Users\\Administrator\\Desktop\\private.key";
	private static String publicPath = "C:\\Users\\Administrator\\Desktop\\public.key";

	public static void main(String args[]) throws Exception {
		KeyStore keystore = KeyStore.getInstance(keystoreType);
		keystore.load(new FileInputStream(new File(keystorePath)), password);
		Key key = keystore.getKey(alias, password);
		Certificate cert = keystore.getCertificate(alias);
		PublicKey publicKey = cert.getPublicKey();
		
		FileWriter fw = new FileWriter(new File(certPath));
		String encodedCert = Base64.getEncoder().encodeToString(cert.getEncoded());
		fw.write("-----BEGIN CERTIFICATE-----\r\n");	//非必须
		fw.write(encodedCert);
		fw.write("\r\n-----END CERTIFICATE-----");	//非必须
		fw.close();
		
		fw = new FileWriter(new File(privatePath));
		String encodedPrivate = Base64.getEncoder().encodeToString(key.getEncoded());
		fw.write("-----BEGIN PRIVATE KEY-----\r\n");	//非必须
		fw.write(encodedPrivate);
		fw.write("\r\n-----END PRIVATE KEY-----");	//非必须
		fw.close();
		
		fw = new FileWriter(new File(publicPath));
		String encodedPublic = Base64.getEncoder().encodeToString(publicKey.getEncoded());
		fw.write("-----BEGIN PUBLIC KEY-----\r\n");	//非必须
		fw.write(encodedPublic);
		fw.write("\r\n-----END PUBLIC KEY-----");	//非必须
		fw.close();
	}

}
//http://happyqing.iteye.com/blog/2139504
