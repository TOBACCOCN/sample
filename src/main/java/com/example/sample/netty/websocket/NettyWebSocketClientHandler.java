package com.example.sample.netty.websocket;

import com.example.sample.util.ErrorPrintUtil;
import io.netty.channel.*;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyWebSocketClientHandler extends SimpleChannelInboundHandler<Object> {

    private static Logger logger = LoggerFactory.getLogger(NettyWebSocketClientHandler.class);

    private WebSocketClientHandshaker handshaker;

    private ChannelPromise handshakeFuture;

    public NettyWebSocketClientHandler(WebSocketClientHandshaker handshaker) {
        super();
        this.handshaker = handshaker;
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
        logger.info(">>>>> RECEIVING MESSAGE: {}", msg.toString());
        Channel channel = ctx.channel();
        if (!handshaker.isHandshakeComplete()) {
            handshaker.finishHandshake(channel, (FullHttpResponse) msg);
            handshakeFuture.setSuccess();
        }

        if (msg instanceof CloseWebSocketFrame) {
            logger.info(">>>>> CLOSE WEBSOCKET FROM SERVER");
            handshaker.close(channel, ((CloseWebSocketFrame) msg).retain());
        } else if (msg instanceof PingWebSocketFrame) {
            logger.info(">>>>> PING FROM SERVER");
            ctx.write(new PongWebSocketFrame(((PingWebSocketFrame) msg).content().retain()));
        } else if (msg instanceof TextWebSocketFrame) {
            String text = ((TextWebSocketFrame) msg).text();
            logger.info("TEXT_MESSAGE: {}", text);
        } else if (msg instanceof BinaryWebSocketFrame) {
            // TODO
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        handshaker.handshake(ctx.channel());
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ErrorPrintUtil.printErrorMsg(logger, cause);
        ctx.close();
    }

}
