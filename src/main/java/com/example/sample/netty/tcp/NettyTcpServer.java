package com.example.sample.netty.tcp;

import com.alibaba.fastjson.JSONObject;
import com.example.sample.util.ErrorPrintUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
public class NettyTcpServer implements Runnable {

    // private static Logger logger = LoggerFactory.getLogger(NettyTcpServer.class);

    private int port;

    public NettyTcpServer(int port) {
        super();
        this.port = port;
    }

    public void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap = bootstrap.group(bossGroup, workerGroup)
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
                            pipeline.addLast(new StringDecoder());
                            pipeline.addLast(new StringEncoder());
                            pipeline.addLast(new NettyTcpServerHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture future = bootstrap.bind(port).sync();
            log.info(">>>>> NETTY TCP SERVER STARTING, PORT: {}", port);
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            ErrorPrintUtil.printErrorMsg(log, e);
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        int port = 8080;
        // 启动服务端
        new Thread(new NettyTcpServer(port)).start();

        // 模拟服务端主动给客户端发送消息
        new Thread(() -> {
            JSONObject message = new JSONObject();
            message.put("message", "I am netty tcp server");
            while (true) {
                Map<String, Channel> requestId2ChannelMap = TcpChannelManager.getRequestId2ChannelMap();
                if (requestId2ChannelMap.size() > 0) {
                    requestId2ChannelMap.keySet().forEach(requestId ->
                            TcpChannelManager.getChannel(requestId).writeAndFlush(message.toString()));
                    try {
                        TimeUnit.MINUTES.sleep(5);
                        // TimeUnit.SECONDS.sleep(15);
                    } catch (InterruptedException e) {
                        ErrorPrintUtil.printErrorMsg(log, e);
                    }
                }
            }
        }).start();
    }

}