package com.example.sample.trans.youdao;

import com.example.sample.util.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class YoudaoTTS {

    // private static Logger logger = LoggerFactory.getLogger(YoudaoTTS.class);

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        Map<String, String> map = new HashMap<>();
        String appKey = "";
        String q = "Has also become a thorn in the side of local governments";
        String salt = UUID.randomUUID().toString().replaceAll("-", "");
        String appSecret = "";
        String langType = "en";
        String format = "wav";
        String sign = MD5Util.encode((appKey + q + salt + appSecret).getBytes());

        map.put("appKey", appKey);
        map.put("q", q);
        map.put("salt", salt);
        map.put("langType", langType);
        map.put("format", format);
        map.put("sign", sign);

        String url = "https://openapi.youdao.com/ttsapi";
        String ttsUrl = getUrlWithQueryString(url, map);

        // 语音合成请求结果
        byte[] result = requestTTS(ttsUrl);
        //合成成功
        if (result != null) {
            File audioFile = new File("D:/" + System.currentTimeMillis() + ".wav");
            FileOutputStream fos = new FileOutputStream(audioFile);
            fos.write(result);
            fos.close();
        }

    }

    /**
     * 根据 api 地址和参数生成请求 URL
     *
     * @param url    请求地址
     * @param params 请求参数
     * @return 拼接请求参数的请求地址
     */
    private static String getUrlWithQueryString(String url, Map<String, String> params) throws UnsupportedEncodingException {
        if (params == null) {
            return url;
        }

        StringBuilder builder = new StringBuilder(url);
        if (url.contains("?")) {
            builder.append("&");
        } else {
            builder.append("?");
        }

        int i = 0;
        for (String key : params.keySet()) {
            String value = params.get(key);
            if (value == null) { // 过滤空的key
                continue;
            }

            if (i != 0) {
                builder.append('&');
            }

            builder.append(key);
            builder.append('=');
            builder.append(URLEncoder.encode(value, StandardCharsets.UTF_8.toString()));

            i++;
        }

        return builder.toString();
    }

    /**
     * 请求语音合成服务
     *
     * @param url 请求地址
     * @return 响应的字节数组
     */
    private static byte[] requestTTS(String url) throws IOException {
        log.info(">>>>> REQUEST URL: [{}]", url);

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        Header[] contentType = httpResponse.getHeaders("Content-Type");
        log.info(">>>>> RESPONSE CONTENT-TYPE: [{}]", contentType[0].getValue());

        byte[] result = null;
        //如果响应是 wav
        if ("audio/x-wav".equals(contentType[0].getValue()) || "audio/mp3".equals(contentType[0].getValue())) {
            HttpEntity httpEntity = httpResponse.getEntity();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            httpResponse.getEntity().writeTo(baos);
            result = baos.toByteArray();
            EntityUtils.consume(httpEntity);
        } else {
            // 响应不是音频流，直接显示结果
            HttpEntity httpEntity = httpResponse.getEntity();
            String json = EntityUtils.toString(httpEntity, StandardCharsets.UTF_8);
            EntityUtils.consume(httpEntity);
            log.info(">>>>> ORIGIN_RESULT: [{}]", json);
        }

        httpResponse.close();
        return result;
    }

}