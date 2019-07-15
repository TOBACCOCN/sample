package com.example.sample.netty.http;

import com.alibaba.fastjson.JSON;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.QueryStringEncoder;
import io.netty.util.AsciiString;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NettyHttpClientTests {

    private String host = "127.0.0.1";
    private int port = 8080;
    private String uri = "/helloworld";
    private Map<AsciiString, Object> headerMap = new HashMap<>();
    private String content;
    private String downloadDir;
    private Map<String, String> paramMap = new HashMap<>();

    @Before
    public void init() {
        headerMap.put(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        paramMap.put("foo", "bar");
        paramMap.put("bar", "barz");
    }

    @Test
    public void httpGet() throws Exception {
        QueryStringEncoder encoder = new QueryStringEncoder(uri);
        paramMap.forEach(encoder::addParam);

        headerMap.put(HttpHeaderNames.CONTENT_LENGTH, 0);

        NettyHttpClient.httpGet(host, port, encoder.toUri(), headerMap, downloadDir);
    }

    @Test
    public void download() throws Exception {
        uri = "/download";
        QueryStringEncoder encoder = new QueryStringEncoder(uri);

        headerMap.put(HttpHeaderNames.CONTENT_LENGTH, 0);
        downloadDir = "D:/";

        NettyHttpClient.httpGet(host, port, encoder.toUri(), headerMap, downloadDir);
    }

    @Test
    public void httpPost() throws Exception {
        UrlencodedStringEncoder encoder = new UrlencodedStringEncoder(uri);

        paramMap.forEach(encoder::addParam);
        content = encoder.toString();

        headerMap.put(HttpHeaderNames.CONTENT_TYPE,
                HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED);
        headerMap.put(HttpHeaderNames.CONTENT_LENGTH, content.getBytes().length);

        NettyHttpClient.httpPost(host, port, encoder.toUri(), headerMap, content);
    }

    @Test
    public void httpPostJson() throws Exception {
        content = JSON.toJSONString(paramMap);

        headerMap.put(HttpHeaderNames.CONTENT_TYPE,
                HttpHeaderValues.APPLICATION_JSON);
        headerMap.put(HttpHeaderNames.CONTENT_LENGTH, content.getBytes().length);


        NettyHttpClient.httpPost(host, port, new URI(uri), headerMap, content);
    }

    @Test
    public void httpPostFormData() throws Exception {
        headerMap.put(HttpHeaderNames.CONTENT_TYPE,
                HttpHeaderValues.MULTIPART_FORM_DATA);

        content = JSON.toJSONString(paramMap);

        List<String> uploadFilePaths = new ArrayList<>();
        uploadFilePaths.add("D:/download/locator_wtwdA8_yk996.zip");
        uploadFilePaths.add("D:/download/docker.txt");

        NettyHttpClient.httpPostFormData(host, port, new URI(uri), headerMap, content, uploadFilePaths);
    }

}
