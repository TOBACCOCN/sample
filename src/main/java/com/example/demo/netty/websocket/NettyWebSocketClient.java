package com.example.demo.netty.websocket;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketClientCompressionHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

public class NettyWebSocketClient {

	private static Logger logger = LoggerFactory.getLogger(NettyWebSocketClient.class);

	private String url;

	private String msg;

	public NettyWebSocketClient() {
		super();
	}

	public NettyWebSocketClient(String url, String msg) {
		super();
		this.url = url;
		this.msg = msg;
	}

	public void run() throws Exception {

		/**
		 * NioEventLoopGroup 是用来处理I/O操作的多线程事件循环器，
		 * Netty提供了许多不同的EventLoopGroup的实现用来处理不同传输协议。 在这个例子中我们实现了一个服务端的应用，
		 * 因此会有2个NioEventLoopGroup会被使用。 第一个经常被叫做‘boss’，用来接收进来的连接。
		 * 第二个经常被叫做‘worker’，用来处理已经被接收的连接， 一旦‘boss’接收到连接，就会把连接信息注册到‘worker’上。
		 * 如何知道多少个线程已经被使用，如何映射到已经创建的Channels上都需要依赖于EventLoopGroup的实现，
		 * 并且可以通过构造函数来配置他们的关系。
		 */
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			URI uri = new URI(url);
			String host = uri.getHost();
			int port = uri.getPort();
			final NettyWebSocketClientHandler handler = new NettyWebSocketClientHandler(WebSocketClientHandshakerFactory
					.newHandshaker(uri, WebSocketVersion.V13, null, true, new DefaultHttpHeaders()));
			/**
			 * ServerBootstrap 是一个启动NIO服务的辅助启动类 你可以在这个服务中直接使用Channel
			 */
			Bootstrap b = new Bootstrap();
			/**
			 * 这一步是必须的，如果没有设置group将会报java.lang.IllegalStateException: group not set异常
			 */
			b = b.group(workerGroup).option(ChannelOption.SO_KEEPALIVE, true)
					/**
					 * ServerSocketChannel以NIO的selector为基础进行实现的，用来接收新的连接 这里告诉Channel如何获取新的连接.
					 */
					.channel(NioSocketChannel.class)
					/**
					 * 这里的事件处理类经常会被用来处理一个最近的已经接收的Channel。 ChannelInitializer是一个特殊的处理类，
					 * 他的目的是帮助使用者配置一个新的Channel。 也许你想通过增加一些处理类比如NettyServerHandler来配置一个新的Channel
					 * 或者其对应的ChannelPipeline来实现你的网络程序。 当你的程序变的复杂时，可能你会增加更多的处理类到pipline上，
					 * 然后提取这些匿名类到最顶层的类上。
					 */
					.handler(new LoggingHandler(LogLevel.DEBUG)).handler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel channel) throws Exception {
							ChannelPipeline pipeline = channel.pipeline();
							SslContext sslContext = SslContextBuilder.forClient()
									.trustManager(InsecureTrustManagerFactory.INSTANCE).build();
							pipeline.addLast(sslContext.newHandler(channel.alloc()));
							pipeline.addLast(new HttpClientCodec());
							pipeline.addLast(new HttpObjectAggregator(8192));
							pipeline.addLast(WebSocketClientCompressionHandler.INSTANCE);
							pipeline.addLast(handler);
						}
					});
			/**
			 * 连接服务端
			 */
			Channel channel = b.connect(host, port).sync().channel();
			logger.debug("netty websocket client start to connect server, host: {}, port: {}", host, port);
			WebSocketFrame frame = new TextWebSocketFrame(msg);
			channel.writeAndFlush(frame);
			handler.handshakeFuture().sync();
		} finally {
			/**
			 * 关闭
			 */
			workerGroup.shutdownGracefully();
		}
	}

	public static void main(String[] args) throws Exception {
		String url = "wss://127.0.0.1:8080/websocket";
		Map<String, Object> reqMap = new HashMap<>();
		reqMap.put("foo", "bar");
		String msg = JSON.toJSONString(reqMap);
		new NettyWebSocketClient(url, msg).run();
	}

}