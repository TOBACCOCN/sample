package com.example.sample.netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyHttpServer {

    // private static Logger logger = LoggerFactory.getLogger(NettyHttpServer.class);

    private int port;

    private String downloadUri;

    private String downloadFilePath;

    private String uploadDir;


    public NettyHttpServer(int port, String downloadUri, String downloadFilePath, String uploadDir) {
        super();
        this.port = port;
        this.downloadUri = downloadUri;
        this.downloadFilePath = downloadFilePath;
        this.uploadDir = uploadDir;
    }

    public void run() throws Exception {
        ServerBootstrap bootstrap = new ServerBootstrap();
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
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
                            pipeline.addLast(new HttpRequestDecoder());
                            pipeline.addLast(new HttpResponseEncoder());
                            pipeline.addLast(new HttpObjectAggregator(10 * 1024 * 1024));
                            pipeline.addLast(new HttpContentCompressor());
                            pipeline.addLast(new NettyHttpServerHandler(downloadUri, downloadFilePath, uploadDir));
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture future = bootstrap.bind(port).sync();
            log.info(">>>>> NETTY HTTP SERVER STARTED, PORT: {}", port);
            future.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8080;
        String downloadUri = "/download";
        String downloadFilePath = "D:/download/locator_wtwdA8_yk996.zip ";
        String uploadDir = "D:/";
        new NettyHttpServer(port, downloadUri, downloadFilePath, uploadDir).run();
    }

}