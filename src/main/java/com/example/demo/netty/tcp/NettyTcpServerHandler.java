package com.example.demo.netty.tcp;

import java.net.InetAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class NettyTcpServerHandler extends SimpleChannelInboundHandler<String> {

	private static Logger logger = LoggerFactory.getLogger(NettyTcpServerHandler.class);

	@Override
	public void channelRead0(ChannelHandlerContext ctx, String msg) {
		logger.debug("MESSAGE: {}", msg);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		logger.debug("CLIENT CONNECTED, id:{}, address: {}", ctx.channel().id(), ctx.channel().remoteAddress());
		ctx.writeAndFlush("success connect to " + InetAddress.getLocalHost().getHostAddress());
		super.channelActive(ctx);
	}

}
