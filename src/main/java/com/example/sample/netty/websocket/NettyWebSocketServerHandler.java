package com.example.sample.netty.websocket;

import com.alibaba.fastjson.JSONObject;
import com.example.sample.util.ErrorPrintUtil;
import com.example.sample.util.SignUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class NettyWebSocketServerHandler extends SimpleChannelInboundHandler<Object> {

    // private static Logger logger = LoggerFactory.getLogger(NettyWebSocketServerHandler.class);

    private WebSocketServerHandshaker handshaker;

    private static final String WEBSOCKET_DEFAULT_HEADER_CONNECTION = "connection";
    private static final String WEBSOCKET_DEFAULT_HEADER_CONTENT_LENGTH = "content-length";
    private static final String WEBSOCKET_DEFAULT_HEADER_UPGRADE = "upgrade";
    private static final String WEBSOCKET_DEFAULT_HEADER_HOST = "host";
    private static final String WEBSOCKET_DEFAULT_HEADER_WEBSOCKET_KEY = "sec-websocket-key";
    private static final String WEBSOCKET_DEFAULT_HEADER_WEBSOCKET_VERSION = "sec-websocket-version";
    private static final String WEBSOCKET_DEFAULT_HEADER_WEBSOCKET_EXTENSIONS = "sec-websocket-extensions";
    private static final String WEBSOCKET_DEFAULT_HEADER_WEBSOCKET_ORIGIN = "sec-websocket-origin";
    private static final String REQUEST_ID = "requestId";
    private static final String SIGN = "sign";
    private static final String UPGRADE = "Upgrade";

    private static final String WEBSOCKET = "websocket";

    private String protocol;
    private String host;
    private int port;

    public NettyWebSocketServerHandler(String protocol, String host, int port) {
        this.protocol = protocol;
        this.host = host;
        this.port = port;
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            handleHttpRequest(ctx, (FullHttpRequest) msg);
        } else if (msg instanceof WebSocketFrame) {
            handleWebsocketFrame(ctx, (WebSocketFrame) msg);
        }
    }

    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        String upgrade = request.headers().get(UPGRADE);
        // 非 websocket 的 http 握手请求处理
        if (!request.decoderResult().isSuccess() || !WEBSOCKET.equals(upgrade)) {
            log.info(">>>>>  NOT HANDSHAKER OF WEBSOCKET");
            ctx.writeAndFlush(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                    HttpResponseStatus.BAD_REQUEST));
            return;
        }

        Channel channel = ctx.channel();
        // allowExtensions 需要为 true
        WebSocketServerHandshakerFactory wsFactory =
                new WebSocketServerHandshakerFactory(protocol + "://" + host + ":" + port,
                        null, true);
        handshaker = wsFactory.newHandshaker(request);
        if (handshaker == null) {
            // 不支持的请求
            log.info(">>>>> REQUEST NOT SUPPORTED");
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(channel);
        } else {
            // handshaker.handshake(channel, request);
            HttpHeaders headers = request.headers();
            Map<String, String> map = new HashMap<>();
            headers.forEach(entry -> map.put(entry.getKey(), entry.getValue()));
            removeWebsocketDefaultHeader(map);
            if (SignUtil.checkSign(map, SIGN)) {
                handshaker.handshake(channel, request);
                WebsocketChannelManager.registerChannel(map.get(REQUEST_ID), channel);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("message", "connect success");
                channel.writeAndFlush(new TextWebSocketFrame(jsonObject.toString()));
            } else {
                handshaker.close(channel, new CloseWebSocketFrame());
            }
        }
    }

    private void removeWebsocketDefaultHeader(Map<String, String> map) {
        map.remove(WEBSOCKET_DEFAULT_HEADER_CONNECTION);
        map.remove(WEBSOCKET_DEFAULT_HEADER_CONTENT_LENGTH);
        map.remove(WEBSOCKET_DEFAULT_HEADER_UPGRADE);
        map.remove(WEBSOCKET_DEFAULT_HEADER_HOST);
        map.remove(WEBSOCKET_DEFAULT_HEADER_WEBSOCKET_KEY);
        map.remove(WEBSOCKET_DEFAULT_HEADER_WEBSOCKET_VERSION);
        map.remove(WEBSOCKET_DEFAULT_HEADER_WEBSOCKET_EXTENSIONS);
        map.remove(WEBSOCKET_DEFAULT_HEADER_WEBSOCKET_ORIGIN);
    }

    private void handleWebsocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
        Channel channel = ctx.channel();
        if (WebsocketChannelManager.getRequestId(channel) == null) {
            JSONObject json = new JSONObject();
            json.put("message", "no permission");
            channel.writeAndFlush(json.toString());
            channel.close();
        } else {
            if (frame instanceof CloseWebSocketFrame) {
                log.info(">>>>> CLOSE WEBSOCKET FROM CLIENT");
                channel.close();
            } else if (frame instanceof PingWebSocketFrame) {
                log.info(">>>>> PING FROM CLIENT");
                ctx.writeAndFlush(new PongWebSocketFrame(frame.content().retain()));
            } else if (frame instanceof TextWebSocketFrame) {
                String text = ((TextWebSocketFrame) frame).text();
                log.info(">>>>> TEXT_MESSAGE: {}", text);
            } else if (frame instanceof BinaryWebSocketFrame) {
                log.info(">>>>> BINARY_MESSAGE_LENGTH: {}", frame.content().readableBytes());
                // TODO
            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        log.info(">>>>> CLIENT CONNECTED, CHANNELID:{}, ADDRESS: {}",
                ctx.channel().id(), ctx.channel().remoteAddress());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // 客户端异常断开、channel.close()，以及服务端 channel.close() 都会触发 channelInActive
        // 客户端空闲也会触发 channelInActive，尤其要注意客户端通过 nginx 长连接到服务端时，nginx 对长连接的保活时长，好像是默认 60 秒
        super.channelInactive(ctx);
        WebsocketChannelManager.unregisterChannel(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // 客户端异常断开、channel.close() 都会触发 exceptionCaught()
        if (cause instanceof IOException) {
            log.info(">>>>> CLIENT CLOSED");
        } else {
            ErrorPrintUtil.printErrorMsg(log, cause);
        }

        Channel channel = ctx.channel();
        if (channel != null) {
            WebsocketChannelManager.unregisterChannel(channel);
        }
    }

}
