package com.example.sample.netty.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.sample.util.ErrorPrintUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.*;
import io.netty.handler.codec.http.multipart.InterfaceHttpData.HttpDataType;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class NettyHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

	// private static Logger logger = LoggerFactory.getLogger(NettyHttpServerHandler.class);

	private String downloadUri;

	private String targetDownloadFilePath;

	private String uploadDir;

	public NettyHttpServerHandler(String downloadUri, String targetDownloadFilePath, String uploadDir) {
		super();
		this.downloadUri = downloadUri;
		this.targetDownloadFilePath = targetDownloadFilePath;
		this.uploadDir = uploadDir;
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
		log.info(">>>>> REQ_METHOD: [{}]", method);
		if (!HttpMethod.GET.equals(method) && !HttpMethod.POST.equals(method)) {
			ctx.writeAndFlush(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.METHOD_NOT_ALLOWED))
					.addListener(ChannelFutureListener.CLOSE);
			return;
		}
		// 2. 请求地址 uri
		String uri = request.uri();
		log.info(">>>>> REQ_URI: [{}]", uri);
		// 3. 请求头
		HttpHeaders headers = request.headers();
		headers.entries().forEach(entry -> {
			log.info(">>>>> REQ_HEADER: [{}] = [{}]", entry.getKey(), entry.getValue());
		});

		ChannelFuture channel = null;
		HttpResponse response = null;
		ByteBuf respByteBuf = null;
		// 4. GET 请求
		if (HttpMethod.GET.equals(method)) {
			if (uri.endsWith(downloadUri)) { // 下载文件
				File file = new File(targetDownloadFilePath);
				if (!file.exists()) {
					log.info(">>>>> FILE NOT EXISTS: [{}]", file.getPath());
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
					log.info(">>>>> REQ_PARAM: [{}]", key);
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
			log.info(">>>>> ORIGIN_REQ_BODY: [{}]", reqBody);

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
				log.info(">>>>> REQ_JSON: [{}]", map);
				respByteBuf = Unpooled.copiedBuffer(JSON.toJSONString(map).getBytes());
			}
			// 5.2 application/x-www-form-urlencoded （表单提交）
			else if (HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString().equals(contentType)) {
				Map<String, Object> map = new HashMap<>();
				// 5.2.1 通过 QueryStringDecoder 解析参数
				QueryStringDecoder decoder = new QueryStringDecoder(reqBody, false);
				Map<String, List<String>> parameters = decoder.parameters();
				parameters.forEach((key, value) -> {
					log.info(">>>>> REQ_PARAM: [{}] = [{}]", key, value);
					map.put(key, value);
				});
				// 5.2.2 通过 HttpPostRequestDecoder 解析参数
				// HttpPostRequestDecoder bodyDecoder = new HttpPostRequestDecoder(new DefaultHttpDataFactory(false), request,
				// 		CharsetUtil.UTF_8);
				// List<InterfaceHttpData> datas = bodyDecoder.getBodyHttpDatas();
				// datas.forEach(data -> {
				// 	Attribute attribute = (Attribute) data;
				// 	try {
				// 		log.info(">>>>> REQ_PARAM: [{}] = [{}]", attribute.getName(), attribute.getValue());
				// 		map.put(attribute.getName(), attribute.getValue());
				// 	} catch (IOException e) {
				// 		ErrorPrintUtil.printErrorMsg(log, e);
				// 	}
				// });
				respByteBuf = Unpooled.copiedBuffer(JSON.toJSONString(map).getBytes());
			}
			// 5.3 multipart/form-data （表单提交， 并且上传文件）
			// 注意这里不要用 equals 判断 ，contentType 值形如 multipart/form-data; boundary=a284e4dbb3815e3b
			// else if (HttpHeaderValues.MULTIPART_FORM_DATA.toString().equals(contentType)) {
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
							log.info(">>>>> REQ_PARAM: [{}] = [{}]", attribute.getName(), attribute.getValue());
							map.put(attribute.getName(), attribute.getValue());
						} catch (IOException e) {
							ErrorPrintUtil.printErrorMsg(log, e);
						}
					}
					// 5.3.2 文件
					else if (data.getHttpDataType() == HttpDataType.FileUpload) {
						FileUpload fileUpload = (FileUpload) data;
						String filename = fileUpload.getFilename();
						log.info(">>>>> REQ_PARAM: [{}] = [{}]", HttpHeaderValues.FILENAME.toString(), filename);
						if (fileUpload.isCompleted()) {
							try {
								fileUpload.renameTo(new File(uploadDir, filename));
								map.put(filename, "SUCCESS");
							} catch (IOException e) {
								ErrorPrintUtil.printErrorMsg(log, e);
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
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		// 出现异常就关闭
		ErrorPrintUtil.printErrorMsg(log, cause);
		if (ctx != null && ctx.channel().isActive()) {
			ctx.close();
		}
	}

}