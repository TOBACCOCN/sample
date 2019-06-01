package com.example.demo.netty.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.activation.MimetypesFileTypeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.util.ErrorPrintUtil;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.FileUpload;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.handler.codec.http.multipart.InterfaceHttpData.HttpDataType;
import io.netty.util.CharsetUtil;

public class NettyHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

	private static Logger logger = LoggerFactory.getLogger(NettyHttpServerHandler.class);

	private String downloadUri;

	private String targetDownloadFilePath;

	private String fileUploadBaseDirectory;

	public NettyHttpServerHandler(String downloadUri, String targetDownloadFilePath, String fileUploadBaseDirectory) {
		super();
		this.downloadUri = downloadUri;
		this.targetDownloadFilePath = targetDownloadFilePath;
		this.fileUploadBaseDirectory = fileUploadBaseDirectory;
	}

	// netty5 HTTP 协议栈浅析与实践：https://www.cnblogs.com/cyfonly/p/5616493.html
	@SuppressWarnings("unchecked")
	@Override
	public void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws IOException {
		// 0. 解码失败
		if (!msg.decoderResult().isSuccess()) {
			ctx.writeAndFlush(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST))
					.addListener(ChannelFutureListener.CLOSE);
			return;
		}

		FullHttpRequest request = (FullHttpRequest) msg;
		// 1. 请求方法（GET、POST。。。）
		HttpMethod method = request.method();
		logger.debug("REQ_METHOD: {}", method);
		if (!HttpMethod.GET.equals(method) && !HttpMethod.POST.equals(method)) {
			ctx.writeAndFlush(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.METHOD_NOT_ALLOWED))
					.addListener(ChannelFutureListener.CLOSE);
			return;
		}
		// 2. 请求地址 uri
		String uri = request.uri();
		logger.debug("REQ_URI: {}", uri);
		// 3. 请求头
		HttpHeaders headers = request.headers();
		headers.entries().forEach(entry -> {
			logger.debug("REQ_HEADER: {} = {}", entry.getKey(), entry.getValue());
		});

		ChannelFuture channel = null;
		HttpResponse response = null;
		ByteBuf respByteBuf = null;
		// 4. GET 请求
		if (HttpMethod.GET.equals(method)) {
			if (uri.endsWith(downloadUri)) { // 下载文件
				File file = new File(targetDownloadFilePath);
				if (!file.exists()) {
					logger.debug("file not exists: {}", file.getPath());
					response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.INTERNAL_SERVER_ERROR);
					ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
					return;
				}
				FileInputStream fis = new FileInputStream(file);
				long length = file.length();
				String mimeType = new MimetypesFileTypeMap().getContentType(file);
				String filename = file.getName();
				byte[] buf = new byte[(int) length];
				fis.read(buf);
				response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
				response.headers().set(HttpHeaderNames.CONTENT_LENGTH, length)
						.set(HttpHeaderNames.CONTENT_TYPE, mimeType).set(HttpHeaderNames.CONTENT_DISPOSITION,
								HttpHeaderValues.ATTACHMENT + "; " + HttpHeaderValues.FILENAME + " = " + filename);
				HttpUtil.setTransferEncodingChunked(response, false);
				ctx.writeAndFlush(response);
				channel = ctx.channel().writeAndFlush(Unpooled.copiedBuffer(buf));
				fis.close();
			} else {
				QueryStringDecoder decoder = new QueryStringDecoder(request.uri());
				Map<String, List<String>> parameters = decoder.parameters();
				parameters.entrySet().forEach(key -> {
					// 4.1 GET 请求参数
					logger.debug("REQ_PARAM: {}", key);
				});
				respByteBuf = Unpooled.copiedBuffer(JSON.toJSONString(parameters).getBytes());
				response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,
						Unpooled.copiedBuffer(JSON.toJSONString(parameters).getBytes()));
				response.headers().set(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON)
						.set(HttpHeaderNames.CONTENT_LENGTH, respByteBuf.readableBytes());
				channel = ctx.writeAndFlush(response);
			}
		}
		// 5. POST 请求
		else if (HttpMethod.POST.equals(method)) {
			String contentType = headers.get(HttpHeaderNames.CONTENT_TYPE);
			String reqBody = request.content().toString(CharsetUtil.UTF_8);
			logger.debug("ORIGIN_REQ_BODY: {}", reqBody);

			// Content-Type 不是
			// application/json、application/x-www-form-urlencoded、multipart/form-data
			if (contentType == null || (!HttpHeaderValues.APPLICATION_JSON.toString().equals(contentType)
					&& !HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString().equals(contentType)
					&& !contentType.contains(HttpHeaderValues.MULTIPART_FORM_DATA.toString()))) {
				ctx.writeAndFlush(
						new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.UNPROCESSABLE_ENTITY))
						.addListener(ChannelFutureListener.CLOSE);
				return;
			}

			// 5.1 application/json （json 数据）
			if (HttpHeaderValues.APPLICATION_JSON.toString().equals(contentType)) {
				Map<String, Object> map = JSON.toJavaObject(JSONObject.parseObject(reqBody), Map.class);
				logger.debug("REQ_JSON: {}", map);
				respByteBuf = Unpooled.copiedBuffer(JSON.toJSONString(map).getBytes());
			}
			// 5.2 application/x-www-form-urlencoded （表单提交）
			else if (HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString().equals(contentType)) {
				QueryStringDecoder decoder = new QueryStringDecoder(reqBody, false);
				Map<String, List<String>> parameters = decoder.parameters();
				parameters.entrySet().forEach(key -> {
					logger.debug("REQ_PARAM: {}", key);
				});
				respByteBuf = Unpooled.copiedBuffer(JSON.toJSONString(parameters).getBytes());
			}
			// 5.3 multipart/form-data （表单提交， 并且上传文件）
			else if (contentType.contains(HttpHeaderValues.MULTIPART_FORM_DATA.toString())) {
				Map<String, Object> map = new HashMap<>();
				HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(new DefaultHttpDataFactory(false), request,
						CharsetUtil.UTF_8);
				List<InterfaceHttpData> datas = decoder.getBodyHttpDatas();
				datas.forEach(data -> {
					// 5.3.1 普通参数
					if (data.getHttpDataType() == HttpDataType.Attribute) {
						Attribute attribute = (Attribute) data;
						try {
							logger.debug("REQ_PARAM: {} = {}", attribute.getName(), attribute.getValue());
							map.put(attribute.getName(), attribute.getValue());
						} catch (IOException e) {
							ErrorPrintUtil.printErrorMsg(logger, e);
						}
					}
					// 5.3.2 文件
					else if (data.getHttpDataType() == HttpDataType.FileUpload) {
						FileUpload fileUpload = (FileUpload) data;
						String filename = fileUpload.getFilename();
						logger.debug("REQ_PARAM: {} = {}", HttpHeaderValues.FILENAME.toString(), filename);
						map.put(HttpHeaderValues.FILENAME.toString(), filename);
						if (fileUpload.isCompleted()) {
							try {
								fileUpload.renameTo(new File(fileUploadBaseDirectory, filename));
							} catch (IOException e) {
								ErrorPrintUtil.printErrorMsg(logger, e);
							}
						}
					}
				});
				respByteBuf = Unpooled.copiedBuffer(JSON.toJSONString(map).getBytes());
			}
			// 6. 响应消息
			response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Objects.requireNonNull(respByteBuf));
			response.headers().set(HttpHeaderNames.CONTENT_LENGTH, respByteBuf.readableBytes());
			response.headers().set(HttpHeaderNames.CONTENT_TYPE,
					HttpHeaderValues.APPLICATION_JSON + "; " + HttpHeaderValues.CHARSET + " = " + CharsetUtil.UTF_8);
			channel = ctx.channel().writeAndFlush(response);
		}

		// 7. 优雅关闭通道
		if (!HttpUtil.isKeepAlive(request) || Objects.requireNonNull(response).status().code() != HttpResponseStatus.OK.code()) {
			Objects.requireNonNull(channel).addListener(ChannelFutureListener.CLOSE);
		}
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// 出现异常就关闭
		ErrorPrintUtil.printErrorMsg(logger, cause);
		if (ctx != null && ctx.channel().isActive()) {
			ctx.close();
		}
	}

}