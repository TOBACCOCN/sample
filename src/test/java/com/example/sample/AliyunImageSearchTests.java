package com.example.sample;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import sun.misc.BASE64Encoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public class AliyunImageSearchTests {

    private Charset charset = StandardCharsets.UTF_8;
    // fill your instanceName
    private String instanceName = "";
    private String baseUrl = "http://imagesearch.cn-shanghai.aliyuncs.com/v2/image/";

    @Test
    public void addImage() throws Exception {
        // 图片长和宽的像素必须都大于等于448，并且小于等于1024
        // String fileName = "C:\\Users\\Administrator\\Desktop\\test.jpg";
        String fileName = "C:\\Users\\Administrator\\Desktop\\test\\lace\\微信图片_20191205162205.jpg";
        byte[] bytes2 = getBytes(fileName);
        Base64 base64 = new Base64();

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("InstanceName", instanceName));
        // fill your PicName and ProductId
        // params.add(new BasicNameValuePair("PicName", "微信图片_20191205162205"));
        params.add(new BasicNameValuePair("PicName", ""));
        // params.add(new BasicNameValuePair("ProductId", "20191205162205"));
        params.add(new BasicNameValuePair("ProductId", ""));
        params.add(new BasicNameValuePair("PicContent", base64.encodeToString(bytes2)));

        String operation = "add";
        String url = baseUrl + operation;
        imageHandle(params, operation, url);
    }

    @Test
    public void searchImage() throws Exception {
        // 图片长和宽的像素必须都大于等于448，并且小于等于1024
        String fileName =  "C:\\Users\\Administrator\\Desktop\\pic\\微信图片_20191205230925.jpg";
        byte[] bytes2 = getBytes(fileName);
        Base64 base64 = new Base64();

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("InstanceName", instanceName));
        params.add(new BasicNameValuePair("PicContent", base64.encodeToString(bytes2)));

        String operation = "search";
        String url = baseUrl + operation;
        imageHandle(params, operation, url);
    }

    @Test
    public void deleteImage() throws Exception {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("InstanceName", instanceName));
        // fill your PicName and ProductId
        // params.add(new BasicNameValuePair("PicName", "微信图片_20191205162205"));
        params.add(new BasicNameValuePair("PicName", ""));
        // params.add(new BasicNameValuePair("ProductId", "20191205162205"));
        params.add(new BasicNameValuePair("ProductId", ""));

        String operation = "delete";
        String url = baseUrl + operation;
        imageHandle(params, operation, url);
    }

    public void imageHandle(List<NameValuePair> params, String operation, String url) throws Exception {
        HttpEntity entity = new UrlEncodedFormEntity(params, charset);
        String content = convert(entity.getContent());
        log.debug(">>>>> REQ_CONTENT: [{}]", content);

        Map<String, String> headers = new HashMap<>();
        String signature = buildSignatureAndHeader(operation, content, headers);
        log.debug(">>>>> SIGNATURE: [{}]", signature);

        // fill your keySecret
        String keySecret = "";
        byte[] signBytes = hmacSHA1Signature(keySecret, signature);
        signature = newStringByBase64(signBytes);
        // fill your accessKeyId
        String accessKeyId = "";
        String authorization = "acs " + accessKeyId + ":" + signature;
        headers.put("authorization", authorization);
        log.debug(">>>>> AUTHORIZATION: [{}]", authorization);

        HttpPost httpPost = new HttpPost(url);
        for (String key : headers.keySet()) {
            httpPost.addHeader(key, headers.get(key));
        }

        httpPost.setEntity(entity);
        String result = access(httpPost);
        log.debug(">>>>> RESULT: [{}]", result);
    }

    private String getMd5(String body) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        return base64en.encode(md5.digest(body.getBytes(charset)));
    }

    private String getGMT() {
        Calendar cd = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.format(cd.getTime());
    }


    private String generateSignatureNonce() {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            int number = random.nextInt(base.length());
            builder.append(base.charAt(number));
        }
        return builder.toString();
    }

    private String buildSignatureAndHeader(String operation, String postContent, Map<String, String> headers) {
        String data = "POST\n";
        String accept = "application/json";
        data += accept + "\n";

        String contentMd5;
        try {
            contentMd5 = getMd5(postContent);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        data += contentMd5 + "\n";

        String contentType = "application/x-www-form-urlencoded; charset=" + charset;
        data += contentType + "\n";

        String gmt = getGMT();
        data += gmt + "\n";

        String method = "HMAC-SHA1";
        data += "x-acs-signature-method:" + method + "\n";

        String signatureNonce = generateSignatureNonce();
        data += "x-acs-signature-nonce:" + signatureNonce + "\n";

        String apiVersion = "2019-03-25";
        data += "x-acs-version:" + apiVersion + "\n";
        data += "/v2/image/" + operation;

        headers.put("x-acs-version", apiVersion);
        headers.put("x-acs-signature-method", method);
        headers.put("x-acs-signature-nonce", signatureNonce);

        headers.put("accept", accept);
        headers.put("content-md5", contentMd5);
        headers.put("content-type", contentType);
        headers.put("date", gmt);

        return data;
    }

    private byte[] hmacSHA1Signature(String secret, String baseString) throws Exception {
        if (secret == null || secret.length() == 0) {
            throw new IOException("secret can not be empty");
        }
        if (baseString == null || baseString.length() == 0) {
            return null;
        }
        Mac mac = Mac.getInstance("HmacSHA1");
        SecretKeySpec keySpec = new SecretKeySpec(secret.getBytes(charset), "AES");
        mac.init(keySpec);
        return mac.doFinal(baseString.getBytes(charset));
    }

    private String newStringByBase64(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        return new String(Base64.encodeBase64(bytes, false), charset);
    }

    private String access(HttpRequestBase httpRequest) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpResponse response;
        String result;
        try {
            httpRequest.setHeader("accept-encoding", "");
            response = client.execute(httpRequest);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity httpEntity = response.getEntity();
                result = EntityUtils.toString(httpEntity);
            } else {
                log.debug(">>>>> RESPONSE_STATUS_CODE: [{}]", response.getStatusLine().getStatusCode());
                HttpEntity httpEntity = response.getEntity();
                result = EntityUtils.toString(httpEntity);
                log.debug(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return result;
    }

    private String convert(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, charset))) {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }
        return stringBuilder.toString();
    }

    private byte[] getBytes(String filePath) throws Exception {
        File file = new File(filePath);
        FileInputStream fis = new FileInputStream(file);
        // picture max size is 2MB
        ByteArrayOutputStream bos = new ByteArrayOutputStream(2000 * 1024);
        byte[] b = new byte[1000];
        int n;
        while ((n = fis.read(b)) != -1) {
            bos.write(b, 0, n);
        }
        fis.close();
        bos.close();
        return bos.toByteArray();
    }

}
