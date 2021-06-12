package com.example.sample;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.sample.util.HttpURLConnectionUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class HttpURLConnectionUtilTest {

    private static final Logger LOG = LoggerFactory.getLogger(HttpURLConnectionUtilTest.class);

    @Test
    public void jpush() throws Exception {
        String url = "https://api.jpush.cn/v3/push";
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Content-Type", "application/json");
        String keySecret = ":";
        String authorization = "Basic " + Base64.getEncoder().encodeToString(keySecret.getBytes(StandardCharsets.UTF_8));
        headerMap.put("Authorization", authorization);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("platform", "android");
        jsonObject.put("audience", "all");
        JSONObject msgJsonObj = new JSONObject();
        msgJsonObj.put("msg_content", "jpush_content_example_XXXXXX");
        jsonObject.put("message", msgJsonObj);
        String param = jsonObject.toString();
        String s = HttpURLConnectionUtil.httpPost(url, headerMap, param);
        LOG.debug(">>>>> RESPONSE: [{}]", s);
    }

}
