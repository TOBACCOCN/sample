package com.example.demo.netty.http;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.HttpPostRequestEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.util.AsciiString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class NettyHttpClient {
	
	private static Logger logger = LoggerFactory.getLogger(NettyHttpClient.class);

	private String host;

	private int port;

	private HttpRequest request;

	private HttpPostRequestEncoder encoder;

	private HttpMethod method;

	private URI uri;

	private Map<AsciiString, Object> headerMap;

	private ByteBuf reqBody;
	
	private String fileDownloadBaseDirectory;

	public NettyHttpClient() {
		super();
	}

	public NettyHttpClient(String host, int port, HttpRequest request, HttpPostRequestEncoder encoder) {
		super();
		this.host = host;
		this.port = port;
		this.request = request;
		this.encoder = encoder;
	}

	public NettyHttpClient(String host, int port, URI uri, HttpMethod method, Map<AsciiString, Object> headerMap,
			ByteBuf reqBody, String fileDownloadBaseDirectory) {
		super();
		this.host = host;
		this.port = port;
		this.method = method;
		this.uri = uri;
		this.headerMap = headerMap;
		this.reqBody = reqBody;
		this.fileDownloadBaseDirectory = fileDownloadBaseDirectory;
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
							pipeline.addLast(new ReadTimeoutHandler(15, TimeUnit.SECONDS));
							pipeline.addLast(new HttpRequestEncoder());
							pipeline.addLast(new HttpResponseDecoder());
							pipeline.addLast(new HttpObjectAggregator(10 * 1024 * 1024));
							pipeline.addLast(new HttpContentDecompressor());
							pipeline.addLast(new ChunkedWriteHandler()); // 上传文件必需
							NettyHttpClientHandler handler = request == null
									? new NettyHttpClientHandler(method, uri, headerMap, reqBody, fileDownloadBaseDirectory)
									: new NettyHttpClientHandler(request, encoder);
							pipeline.addLast(handler);
						}
					});
			/**
			 * 连接服务端
			 */
			ChannelFuture future = b.connect(host, port).sync();
			logger.debug("netty http client start to connect server, host: {}, port: {}", host, port);
			/**
			 * 这里会一直等待，直到socket被关闭
			 */
			future.channel().closeFuture().sync();
		} finally {
			/**
			 * 关闭
			 */
			workerGroup.shutdownGracefully();
		}
	}

}