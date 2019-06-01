package com.example.demo.netty.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

public class NettyWebSocketServerHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

	private static Logger logger = LoggerFactory.getLogger(NettyWebSocketServerHandler.class);

	@Override
	public void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
		if (frame instanceof TextWebSocketFrame) {
			String text = ((TextWebSocketFrame) frame).text();
			logger.debug("TEXT MESSAGE: {}", text);
			ctx.writeAndFlush(new TextWebSocketFrame(text.toUpperCase()));
		} else if (frame instanceof BinaryWebSocketFrame) {
			// TODO
		}
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.writeAndFlush(new TextWebSocketFrame("success connected to "));
		super.channelActive(ctx);
	}

}
