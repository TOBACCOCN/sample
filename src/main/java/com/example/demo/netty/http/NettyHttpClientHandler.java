package com.example.demo.netty.http;

import com.example.demo.util.ErrorPrintUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.HttpPostRequestEncoder;
import io.netty.util.AsciiString;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;
import java.util.Map;

public class NettyHttpClientHandler extends SimpleChannelInboundHandler<HttpContent> {

	private static Logger logger = LoggerFactory.getLogger(NettyHttpClientHandler.class);

	private HttpRequest request;

	private HttpPostRequestEncoder encoder;

	private HttpMethod method;

	private URI uri;

	private Map<AsciiString, Object> headerMap;

	private ByteBuf reqBody;
	
	private String fileDownloadBaseDirectory;

	public NettyHttpClientHandler() {
		super();
	}

	public NettyHttpClientHandler(HttpRequest request, HttpPostRequestEncoder encoder) {
		super();
		this.request = request;
		this.encoder = encoder;
	}

	public NettyHttpClientHandler(HttpMethod method, URI uri, Map<AsciiString, Object> headerMap, ByteBuf reqBody, String fileDownloadBaseDirectory) {
		super();
		this.method = method;
		this.uri = uri;
		this.headerMap = headerMap;
		this.reqBody = reqBody;
		this.fileDownloadBaseDirectory = fileDownloadBaseDirectory;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, HttpContent msg) throws Exception {
		// 接收到服务端响应
		if (msg instanceof HttpResponse) {
			HttpResponse response = (HttpResponse) msg;
			response.headers().entries().forEach(entry -> {
				logger.debug("RESPONSE_HEADER: {} = {}", entry.getKey(), entry.getValue());
			});
			if (msg.content() == null) {
				logger.debug("ORGIN_RESPONSE_BODY: {}", msg.content());
				return;
			}
			logger.debug("ORGIN_RESPONSE_BODY: {}", msg.content().toString(CharsetUtil.UTF_8));
			if (HttpHeaderValues.APPLICATION_OCTET_STREAM.toString()
					.equals(response.headers().get(HttpHeaderNames.CONTENT_TYPE))) {
				String filename = response.headers().get(HttpHeaderNames.CONTENT_DISPOSITION).split(";")[1]
						.split("=")[1];
				FileOutputStream fos = new FileOutputStream(new File(fileDownloadBaseDirectory, filename));
				ByteBuf byteBuf = msg.content();

				int readableLength = byteBuf.readableBytes();
				while (readableLength > 0) {
					byte[] buf = new byte[8192];
					if (readableLength > buf.length) {
						byteBuf.readBytes(buf);
						fos.write(buf, 0, buf.length);
					} else {
						byte[] lefts = new byte[readableLength];
						byteBuf.readBytes(lefts);
						fos.write(lefts, 0, readableLength);
					}
					readableLength = byteBuf.readableBytes();
				}
				fos.flush();
				fos.close();
			}
		}
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		if (request != null) {
			// POST 请求之 multipart/form-data
			ctx.writeAndFlush(request);
			if (encoder.isChunked()) {
				ctx.writeAndFlush(encoder);
			}
			if (encoder.isMultipart()) {
				encoder.cleanFiles();
			}
		} else {
			// 发送 GET 请求
			if (HttpMethod.GET == method) {
				FullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, method, uri.toASCIIString());
				headerMap.keySet().forEach(key -> {
					request.headers().set(key, headerMap.get(key));
				});
				ctx.writeAndFlush(request);
			}
			// 发送 POST 请求
			else {
				FullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, method, uri.toASCIIString(),
						reqBody);
				headerMap.keySet().forEach(key -> {
					request.headers().set(key, headerMap.get(key));
				});
				ctx.writeAndFlush(request);
			}
		}
	}

	// @Override
	// public void channelInactive(ChannelHandlerContext ctx) throws Exception {
	// 	super.channelInactive(ctx);
	// 	ctx.channel().close();
	// }

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
