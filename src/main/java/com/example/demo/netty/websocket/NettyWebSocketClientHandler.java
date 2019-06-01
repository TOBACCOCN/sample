package com.example.demo.netty.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.util.ErrorPrintUtil;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

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
        Channel channel = ctx.channel();
        if (!handshaker.isHandshakeComplete()) {
            handshaker.finishHandshake(channel, (FullHttpResponse) msg);
            handshakeFuture.setSuccess();
            return;
        }
        WebSocketFrame frame = (WebSocketFrame) msg;
        if (frame instanceof TextWebSocketFrame) {
        	logger.debug("TEXT MESSAGE: {}", ((TextWebSocketFrame) msg).text());
        }  else if (frame instanceof PongWebSocketFrame) {
        	logger.debug("TEXT MESSAGE: {}", ((TextWebSocketFrame) msg).text());
        } else if (frame instanceof CloseWebSocketFrame) {
        	logger.debug("TEXT MESSAGE: {}", ((TextWebSocketFrame) msg).text());
        	channel.close();
        }
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		handshaker.handshake(ctx.channel());
		WebSocketFrame frame = new TextWebSocketFrame("hello i am websocket client");
		ctx.writeAndFlush(frame);
	}
	
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ErrorPrintUtil.printErrorMsg(logger, cause);
        if (!handshakeFuture.isDone()) {
            handshakeFuture.setFailure(cause);
        }
        ctx.close();
    }

}
