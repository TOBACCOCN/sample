package com.example.sample.netty.http;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
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

import javax.net.ssl.SSLException;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class NettyHttpClient {

    private static Logger logger = LoggerFactory.getLogger(NettyHttpClient.class);

    private static Bootstrap initBootStrap(Bootstrap bootstrap, EventLoopGroup workerGroup) {
        bootstrap = bootstrap.group(workerGroup).option(ChannelOption.SO_KEEPALIVE, true)
                .channel(NioSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.DEBUG));
        return bootstrap;
    }

    private static ChannelPipeline initPipeline(SocketChannel channel) throws SSLException {
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
        return pipeline;
    }

    public static void httpGet(String host, int port, URI uri, Map<AsciiString, Object> headerMap, String downloadDir) throws Exception {
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        bootstrap = initBootStrap(bootstrap, workerGroup);
        try {
            bootstrap = bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel channel) throws Exception {
                    ChannelPipeline pipeline = initPipeline(channel);
                    pipeline.addLast(new NettyHttpClientHandler(HttpMethod.GET, uri, headerMap,
                            null, downloadDir, null));
                }
            });
            ChannelFuture future = bootstrap.connect(host, port).sync();
            logger.info(">>>> NETTY HTTP CLIENT START TO CONNECT SERVER, HOST: {}, PORT: {}", host, port);
            future.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    public static void httpPost(String host, int port, URI uri, Map<AsciiString, Object> headerMap, String content) throws Exception {
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        bootstrap = initBootStrap(bootstrap, workerGroup);
        try {
            bootstrap = bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel channel) throws Exception {
                    ChannelPipeline pipeline = initPipeline(channel);
                    pipeline.addLast(new NettyHttpClientHandler(HttpMethod.POST, uri, headerMap,
                            content, null, null));
                }
            });
            ChannelFuture future = bootstrap.connect(host, port).sync();
            logger.info(">>>> NETTY HTTP CLIENT START TO CONNECT SERVER, HOST: {}, PORT: {}", host, port);
            future.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    public static void httpPostFormData(String host, int port, URI uri, Map<AsciiString, Object> headerMap,
                                        String json, List<String> uploadFilePaths) throws Exception {
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        bootstrap = initBootStrap(bootstrap, workerGroup);
        try {
            bootstrap = bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel channel) throws Exception {
                    ChannelPipeline pipeline = initPipeline(channel);
                    pipeline.addLast(new NettyHttpClientHandler(HttpMethod.POST, uri, headerMap,
                            json, null, uploadFilePaths));
                }
            });
            ChannelFuture future = bootstrap.connect(host, port).sync();
            logger.info(">>>> NETTY HTTP CLIENT START TO CONNECT SERVER, HOST: {}, PORT: {}", host, port);
            future.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

}