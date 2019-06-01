package com.example.demo.netty.http;

import com.alibaba.fastjson.JSON;
import com.example.demo.util.ErrorPrintUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.HttpPostRequestEncoder;
import io.netty.handler.codec.http.multipart.HttpPostRequestEncoder.ErrorDataEncoderException;
import io.netty.util.AsciiString;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class NettyHttpClientTest {
	
	private static Logger logger = LoggerFactory.getLogger(NettyHttpClientTest.class);

	private String host = "127.0.0.1";

	private int port = 8080;
	
	private String uri = "/helloworld";

	private Map<AsciiString, Object> headerMap = new HashMap<>();

	private ByteBuf reqBody;
	
	private String fileDownloadBaseDirectory;
	
	private Map<String, String> paramMap = new HashMap<>();

	@Before
	public void doBefore() {
		headerMap.put(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
		paramMap.put("foo", "bar");
	}

	@Test
	// GET 请求
	public void doGET() throws Exception {
		headerMap.put(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
		headerMap.put(HttpHeaderNames.CONTENT_LENGTH, 0);
		uri = "/download";
		QueryStringEncoder encoder = new QueryStringEncoder(uri);
		paramMap.forEach(encoder::addParam);
		new NettyHttpClient(host, port, encoder.toUri(), HttpMethod.GET, headerMap, reqBody, fileDownloadBaseDirectory).run();
	}

	@Test
	// GET 请求
	public void doGETDownload() throws Exception {
		headerMap.put(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
		headerMap.put(HttpHeaderNames.CONTENT_LENGTH, 0);
		uri = "/download";
		fileDownloadBaseDirectory = "D:/";
		QueryStringEncoder encoder = new QueryStringEncoder(uri);
		new NettyHttpClient(host, port, encoder.toUri(), HttpMethod.GET, headerMap, reqBody, fileDownloadBaseDirectory).run();
	}

	@Test
	// POST 请求（json）
	public void doPOSTJson() throws Exception {
		Map<String, String> bodyMap = new HashMap<>();
		paramMap.forEach(bodyMap::put);
		reqBody = Unpooled.wrappedBuffer(JSON.toJSONString(bodyMap).getBytes());
		headerMap.put(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON);
		headerMap.put(HttpHeaderNames.CONTENT_LENGTH, reqBody.readableBytes());
		new NettyHttpClient(host, port, new URI(uri), HttpMethod.POST, headerMap, reqBody, fileDownloadBaseDirectory).run();
	}

	@Test
	// POST 请求（application/x-www-form-urlencoded）
	public void doPOSTFormUrlencoded() throws Exception {
		UrlencodedStringEncoder encoder = new UrlencodedStringEncoder(uri);
		paramMap.forEach(encoder::addParam);
		ByteBuf reqBody = Unpooled.wrappedBuffer(encoder.toString().getBytes());
		headerMap.put(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED);
		headerMap.put(HttpHeaderNames.CONTENT_LENGTH, reqBody.readableBytes());
		new NettyHttpClient(host, port, encoder.toUri(), HttpMethod.POST, headerMap, reqBody, fileDownloadBaseDirectory).run();
	}

	@Test
	// POST 请求（multipart/form-data）
	public void doPOSTFromData() throws Exception {
		HttpRequest request = new DefaultHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST, uri);
		request.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE).set(HttpHeaderNames.CONTENT_TYPE,
				HttpHeaderValues.MULTIPART_FORM_DATA);
		HttpPostRequestEncoder encoder = new HttpPostRequestEncoder(request, true);
		paramMap.forEach((key, value) -> {
			try {
				encoder.addBodyAttribute(key, value);
			} catch (ErrorDataEncoderException e) {
				ErrorPrintUtil.printErrorMsg(logger, e);
			}
		});
		encoder.addBodyFileUpload("file", new File("D:/download/locator_wtwdA8_yk996.zip"), null, false);

		// 重要，不能省，当只有键值对参数没有文件时，需要返回 request 赋值给前文 request：
		// request = encoder.finalizeRequest();
		encoder.finalizeRequest();

		new NettyHttpClient(host, port, request, encoder).run();
	}
	
	@Test
	// POST 请求（application/xml）
	public void doPOSTXml() throws Exception {
		UrlencodedStringEncoder encoder = new UrlencodedStringEncoder(uri);
		paramMap.forEach(encoder::addParam);
		reqBody = Unpooled.wrappedBuffer(encoder.toString().getBytes());
		headerMap.put(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.TEXT_PLAIN);
		headerMap.put(HttpHeaderNames.CONTENT_LENGTH, reqBody.readableBytes());
		new NettyHttpClient(host, port, encoder.toUri(), HttpMethod.POST, headerMap, reqBody, fileDownloadBaseDirectory).run();
	}

	@Test
	// PUT 请求
	public void doPUT() throws Exception {
		UrlencodedStringEncoder encoder = new UrlencodedStringEncoder(uri);
		paramMap.forEach(encoder::addParam);
		reqBody = Unpooled.wrappedBuffer(encoder.toString().getBytes());
		headerMap.put(HttpHeaderNames.CONTENT_LENGTH, reqBody.readableBytes());
		new NettyHttpClient(host, port, encoder.toUri(), HttpMethod.PUT, headerMap, reqBody, fileDownloadBaseDirectory).run();
	}

}
