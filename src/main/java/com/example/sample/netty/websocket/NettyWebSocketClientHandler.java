package com.example.sample.netty.websocket;

import com.alibaba.fastjson.JSONObject;
import com.example.sample.util.ErrorPrintUtil;
import io.netty.channel.*;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyWebSocketClientHandler extends SimpleChannelInboundHandler<Object> {

    // private static Logger logger = LoggerFactory.getLogger(NettyWebSocketClientHandler.class);

    private static final String MESSAGE = "message";
    private static final String MESSAGE_CONNECT_SUCCESS = "connect success";

    private WebSocketClientHandshaker handshaker;
    private String requestId;

    private ChannelPromise handshakeFuture;

    public NettyWebSocketClientHandler(WebSocketClientHandshaker handshaker, String requestId) {
        super();
        this.handshaker = handshaker;
        this.requestId = requestId;
    }

    public ChannelFuture handshakeFuture() {
        return handshakeFuture;
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        handshakeFuture = ctx.newPromise();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
        log.info(">>>>> RECEIVING MESSAGE: {}", msg.toString());
        Channel channel = ctx.channel();
        if (!handshaker.isHandshakeComplete()) {
            handshaker.finishHandshake(channel, (FullHttpResponse) msg);
            handshakeFuture.setSuccess();
        }

        if (msg instanceof CloseWebSocketFrame) {
            log.info(">>>>> CLOSE WEBSOCKET FROM SERVER");
            handshaker.close(channel, ((CloseWebSocketFrame) msg).retain());
        } else if (msg instanceof PongWebSocketFrame) {
            log.info(">>>>> PONG FROM SERVER");
        } else if (msg instanceof TextWebSocketFrame) {
            String text = ((TextWebSocketFrame) msg).text();
            log.info("TEXT_MESSAGE: {}", text);

            JSONObject jsonObject = JSONObject.parseObject(text);
            if (MESSAGE_CONNECT_SUCCESS.equals(jsonObject.getString(MESSAGE))) {
                WebsocketChannelManager.connectSuccess(requestId, ctx.channel());
            }
        } else if (msg instanceof BinaryWebSocketFrame) {
            // TODO
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        handshaker.handshake(ctx.channel());
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ErrorPrintUtil.printErrorMsg(log, cause);
        ctx.close();
    }

}
