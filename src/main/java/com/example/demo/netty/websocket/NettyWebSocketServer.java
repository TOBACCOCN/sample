package com.example.demo.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyWebSocketServer {

	private static Logger logger = LoggerFactory.getLogger(NettyWebSocketServer.class);

	private int port;
	

	public NettyWebSocketServer(int port) {
		super();
		this.port = port;
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
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			/**
			 * ServerBootstrap 是一个启动NIO服务的辅助启动类 你可以在这个服务中直接使用Channel
			 */
			ServerBootstrap b = new ServerBootstrap();
			/**
			 * 这一步是必须的，如果没有设置group将会报java.lang.IllegalStateException: group not set异常
			 */
			b = b.group(bossGroup, workerGroup)
					/**
					 * ServerSocketChannel以NIO的selector为基础进行实现的，用来接收新的连接 这里告诉Channel如何获取新的连接.
					 */
					.channel(NioServerSocketChannel.class)
					/**
					 * 这里的事件处理类经常会被用来处理一个最近的已经接收的Channel。 ChannelInitializer是一个特殊的处理类，
					 * 他的目的是帮助使用者配置一个新的Channel。 也许你想通过增加一些处理类比如NettyServerHandler来配置一个新的Channel
					 * 或者其对应的ChannelPipeline来实现你的网络程序。 当你的程序变的复杂时，可能你会增加更多的处理类到pipline上，
					 * 然后提取这些匿名类到最顶层的类上。
					 */
					.handler(new LoggingHandler(LogLevel.DEBUG)).childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel channel) throws Exception {
							// 1.使用 openssl 工具签名工具生成自签名证书
							// SSLEngine sslEngine = SSLContextFactory.getSslContext().createSSLEngine();
							// sslEngine.setUseClientMode(false);
							// SslHandler sslHandler = new SslHandler(sslEngine);

							// 2.使用 netty 自带类创建自签名证书
							SelfSignedCertificate certificate = new SelfSignedCertificate();
							SslHandler sslHandler = SslContextBuilder.forServer(certificate.certificate(), certificate.privateKey()).build().newHandler(channel.alloc());
							
							ChannelPipeline pipeline = channel.pipeline();
							pipeline.addLast(sslHandler);
							pipeline.addLast(new HttpServerCodec());
					        pipeline.addLast(new HttpObjectAggregator(65536));
					        pipeline.addLast(new WebSocketServerCompressionHandler());
					        pipeline.addLast(new NettyWebSocketServerHandler());
						}
					})
					/**
					 * 你可以设置这里指定的通道实现的配置参数。 我们正在写一个TCP/IP的服务端，
					 * 因此我们被允许设置socket的参数选项比如tcpNoDelay和keepAlive。
					 * 请参考ChannelOption和详细的ChannelConfig实现的接口文档以此可以对ChannelOptions的有一个大概的认识。
					 */
					.option(ChannelOption.SO_BACKLOG, 128)
					/**
					 * option()是提供给NioServerSocketChannel用来接收进来的连接。
					 * childOption()是提供给由父管道ServerChannel接收到的连接， 在这个例子中也是NioServerSocketChannel。
					 */
					.childOption(ChannelOption.SO_KEEPALIVE, true);
			/**
			 * 绑定端口并启动去接收进来的连接
			 */
			ChannelFuture future = b.bind(port).sync();
			logger.debug("netty websocket server starting, port: {}", port);
			/**
			 * 这里会一直等待，直到socket被关闭
			 */
			future.channel().closeFuture().sync();
		} finally {
			/**
			 * 关闭
			 */
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}

	public static void main(String[] args) throws Exception {
		int port = 8080;
		new NettyWebSocketServer(port).run();
	}
	
}