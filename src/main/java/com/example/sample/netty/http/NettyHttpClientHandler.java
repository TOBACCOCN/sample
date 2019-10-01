package com.example.sample.netty.http;

import com.alibaba.fastjson.JSON;
import com.example.sample.util.ErrorPrintUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.HttpPostRequestEncoder;
import io.netty.util.AsciiString;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class NettyHttpClientHandler extends SimpleChannelInboundHandler<HttpContent> {

    // private static Logger logger = LoggerFactory.getLogger(NettyHttpClientHandler.class);

    private HttpMethod method;

    private URI uri;

    private Map<AsciiString, Object> headerMap;

    private String content;

    private String downloadDir;

    private List<String> uploadFilePaths;

    public NettyHttpClientHandler(HttpMethod method, URI uri, Map<AsciiString, Object> headerMap,
                                  String content, String downloadDir, List<String> uploadFilePaths) {
        super();
        this.method = method;
        this.uri = uri;
        this.headerMap = headerMap;
        this.content = content;
        this.downloadDir = downloadDir;
        this.uploadFilePaths = uploadFilePaths;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpContent msg) throws Exception {
        // 接收到服务端响应
        if (msg instanceof HttpResponse) {
            HttpResponse response = (HttpResponse) msg;
            response.headers().entries().forEach(entry -> {
                log.info(">>>>> RESPONSE_HEADER: {} = {}", entry.getKey(), entry.getValue());
            });
            if (msg.content() == null) {
                log.info(">>>>> ORGIN_RESPONSE_BODY: {}", msg.content());
                return;
            }
            log.info(">>>>> ORGIN_RESPONSE_BODY: {}", msg.content().toString(CharsetUtil.UTF_8));
            if (HttpHeaderValues.APPLICATION_OCTET_STREAM.toString()
                    .equals(response.headers().get(HttpHeaderNames.CONTENT_TYPE))) {
                String filename = response.headers().get(HttpHeaderNames.CONTENT_DISPOSITION).split(";")[1]
                        .split("=")[1];
                FileOutputStream fos = new FileOutputStream(new File(downloadDir, filename));
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
    @SuppressWarnings("unchecked")
    public void channelActive(ChannelHandlerContext ctx) throws HttpPostRequestEncoder.ErrorDataEncoderException {
        // POST 请求之 multipart/form-data
        if ((uploadFilePaths != null && uploadFilePaths.size() > 0)
                || (headerMap != null
                && HttpHeaderValues.MULTIPART_FORM_DATA.equals(headerMap.get(HttpHeaderNames.CONTENT_TYPE)))) {
            HttpRequest request = new DefaultHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST, uri.toASCIIString());
            request.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
            HttpPostRequestEncoder encoder = new HttpPostRequestEncoder(request, true);
            Map<String, String> paramMap = JSON.parseObject(content, Map.class);
            paramMap.forEach((key, value) -> {
                try {
                    encoder.addBodyAttribute(key, value);
                } catch (HttpPostRequestEncoder.ErrorDataEncoderException e) {
                    ErrorPrintUtil.printErrorMsg(log, e);
                }
            });

            List<File> files = new ArrayList<>();
            uploadFilePaths.forEach(uploadFilePath -> {
                File file = new File(uploadFilePath);
                if (file.exists()) {
                    files.add(file);
                    try {
                        // addBodyFileUpload 第一个参数本来应该是 "file"，
                        // 如同 Content-Disposition: form-data; name="file"; filename=YOUR_FILENAME 中的 name 的值，
                        // 但是会导致传多个文件时，只能传过去最后一个
                        encoder.addBodyFileUpload(file.getName(), file, null, false);
                    } catch (HttpPostRequestEncoder.ErrorDataEncoderException e) {
                        ErrorPrintUtil.printErrorMsg(log, e);
                    }
                }
            });
            // 一次添加多个 FileUpload 也无效
            // File[] fileArray = new File[files.size()];
            // files.toArray(fileArray);
            // String[] contentTypes = new String[files.size()];
            // boolean[] booleans = new boolean[files.size()];
            // encoder.addBodyFileUploads("file", fileArray, contentTypes, booleans);

            encoder.finalizeRequest();
            // 当只有键值对参数没有文件时，需要返回 request 赋值给前文 request：
            // request = encoder.finalizeRequest();

            ctx.writeAndFlush(request);
            if (encoder.isChunked()) {
                ctx.writeAndFlush(encoder);
            }
            if (encoder.isMultipart()) {
                encoder.cleanFiles();
            }
        }
        else {
            // 发送 GET 请求
            if (HttpMethod.GET == method) {
                FullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, method,
                        uri.toASCIIString());
                headerMap.forEach(request.headers()::set);
                ctx.writeAndFlush(request);
            }
            // 发送 POST 请求
            else {
                FullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, method,
                        uri.toASCIIString(), Unpooled.wrappedBuffer(content.getBytes()));
                headerMap.forEach(request.headers()::set);
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
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // 出现异常，关闭通道
        ErrorPrintUtil.printErrorMsg(log, cause);
        if (ctx != null && ctx.channel().isActive()) {
            ctx.close();
        }
    }

}
