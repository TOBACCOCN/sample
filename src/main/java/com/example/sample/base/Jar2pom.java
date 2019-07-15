package com.example.sample.base;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.dom.DOMElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Objects;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/**
 * 通过Jar包SHA1或MD5生成Pom文件
 */
public class Jar2pom {

    private static Logger logger = LoggerFactory.getLogger(Jar2pom.class);

    /**
     * Maven 库
     */
    private static final String nexusUrl = "http://59.110.144.164/nexus/service/local/lucene/search";

    public static void main(String[] args) {
        //先通过Jar的SHA1查询 如果不存在则解析Manifest查询
        File libs = new File("D:\\git\\eliteai\\yunying\\target\\trans\\WEB-INF\\lib");
        for (File jar : Objects.requireNonNull(libs.listFiles())) {
            logger.info("<!-- {} -->", jar.getName());
            if (!getPomByChecksum(jar).isTextOnly()) {
                logger.info(getPomByChecksum(jar).asXML());
            } else if (!getPomByManifest(jar).isTextOnly()) {
                logger.info("<!--  Search by Manifest -->");
                logger.info(getPomByManifest(jar).asXML());
            } else {
                logger.info("<!-- Found no data -->");
            }
        }

    }

    /**
     * 通过Jar SHA1返回Pom dependency
     */
    private static Element getPomByChecksum(File file) {
        String checkSum = getCheckSum(file, "SHA1");
        String xml = doGet(nexusUrl + "?sha1=" + checkSum);
        return assemblePomElement(xml);
    }

    /**
     * 通过Jar Manifest返回Pom dependency
     */
    private static Element getPomByManifest(File file) {
        try {
            JarFile jarfile = new JarFile(file);
            Manifest manifest = jarfile.getManifest();
            jarfile.close();
            if (null == manifest) {
                return new DOMElement("dependency");
            }
            String a = null, v = null;
            if (manifest.getMainAttributes().containsKey(new Attributes.Name("Extension-Name"))) {
                a = manifest.getMainAttributes().getValue(new Attributes.Name("Extension-Name"));
            } else if (manifest.getMainAttributes().containsKey(new Attributes.Name("Implementation-Title"))) {
                a = manifest.getMainAttributes().getValue(new Attributes.Name("Implementation-Title"));
            } else if (manifest.getMainAttributes().containsKey(new Attributes.Name("Specification-Title"))) {
                a = manifest.getMainAttributes().getValue(new Attributes.Name("Specification-Title"));
            }
            if (a != null && a.length() != 0) {
                a = a.replace("\"", "").replace(" ", "-");
            }
            if (manifest.getMainAttributes().containsKey(new Attributes.Name("Bundle-Version"))) {
                v = manifest.getMainAttributes().getValue(new Attributes.Name("Bundle-Version"));
            } else if (manifest.getMainAttributes().containsKey(new Attributes.Name("Implementation-Version"))) {
                v = manifest.getMainAttributes().getValue(new Attributes.Name("Implementation-Version"));
            } else if (manifest.getMainAttributes().containsKey(new Attributes.Name("Specification-Version"))) {
                v = manifest.getMainAttributes().getValue(new Attributes.Name("Specification-Version"));
            }
            if (v != null && v.length() != 0) {
                v = v.replace("\"", "").replace(" ", "-");
            }
            String xml = doGet(nexusUrl + "?a=" + a + "&v=" + v);
            return assemblePomElement(xml);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new DOMElement("dependency");
    }

    /**
     * 解析获取的XML 组装dependency
     */
    private static Element assemblePomElement(String xml) {
        Element dependency = new DOMElement("dependency");

        if (xml != null && xml.length() != 0) {
            try {
                Document document = DocumentHelper.parseText(xml);
                Element dataElement = document.getRootElement().element("data");
                if (dataElement.getText() != null && dataElement.getText().length() != 0) {
                    Element artifactElement = dataElement.element("artifact");
                    dependency.add((Element) artifactElement.element("groupId").clone());
                    dependency.add((Element) artifactElement.element("artifactId").clone());
                    dependency.add((Element) artifactElement.element("version").clone());
                }
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }
        return dependency;
    }

    /**
     * 发起Get请求
     */
    private static String doGet(String url) {
        String srtResult = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            srtResult = EntityUtils.toString(httpResponse.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return srtResult;
    }

    /**
     * 计算CheckSum
     */
    private static String getCheckSum(File file, String algorithm) {
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest;
        FileInputStream in;
        byte[] buffer = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance(algorithm);
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());
        return bigInt.toString(16);
    }

}