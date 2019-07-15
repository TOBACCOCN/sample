package com.example.sample.netty.websocket;

import com.alibaba.fastjson.JSONObject;
import com.example.sample.util.ErrorPrintUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class NettyWebSocketServer implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(NettyWebSocketServer.class);

    private String protocol;

    private String host;

    private int port;

    public NettyWebSocketServer(String protocol, String host, int port) {
        super();
        this.protocol = protocol;
        this.host = host;
        this.port = port;
    }

    public void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap = serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
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
                            pipeline.addLast(new NettyWebSocketServerHandler(protocol, host, port));
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture future = serverBootstrap.bind(port).sync();
            logger.info(">>>>> NETTY WEBSOCKET SERVER STARTING, PORT: {}", port);
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            ErrorPrintUtil.printErrorMsg(logger, e);
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        String protocol = "wss";
        String host = "127.0.0.1";
        int port = 8080;
        new Thread(new NettyWebSocketServer(protocol, host, port)).start();

        new Thread(() -> {
            Map<String, Channel> channelMap = WebsocketChannelManager.getChannelMap();
            JSONObject json = new JSONObject();
            json.put("message", "Hello! What's your name?");
            try {
                while (true) {
                    if (channelMap.size() > 0) {
                        channelMap.forEach((id, channel) -> channel.writeAndFlush(new TextWebSocketFrame(json.toString())));
                    }
                    Thread.sleep(1000 * 60 * 5);
                }
            } catch (InterruptedException e) {
                ErrorPrintUtil.printErrorMsg(logger, e);
            }
        }).start();
    }

}