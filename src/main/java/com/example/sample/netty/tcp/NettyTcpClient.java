package com.example.sample.netty.tcp;

import com.alibaba.fastjson.JSON;
import com.example.sample.util.ErrorPrintUtil;
import com.example.sample.util.SignUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NettyTcpClient implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(NettyTcpClient.class);

    private static Channel channel;
    private String host;
    private int port;
    private Map<String, String> headerMap;
    static boolean connected = false;

    public NettyTcpClient(String host, int port, Map<String, String> headerMap) {
        super();
        this.host = host;
        this.port = port;
        this.headerMap = headerMap;
    }

    public void run() {
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            bootstrap = bootstrap.group(workerGroup).option(ChannelOption.SO_KEEPALIVE, true)
                    .channel(NioSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.DEBUG)).handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel channel) throws Exception {
                            ChannelPipeline pipeline = channel.pipeline();
                            SslContext sslContext = SslContextBuilder.forClient()
                                    .trustManager(InsecureTrustManagerFactory.INSTANCE).build();
                            pipeline.addLast(sslContext.newHandler(channel.alloc()));
                            pipeline.addLast(new StringEncoder());
                            pipeline.addLast(new StringDecoder());
                            pipeline.addLast(new NettyTcpClientHandler(headerMap));
                        }
                    });
            ChannelFuture future = bootstrap.connect(host, port).sync();
            logger.info(">>>>> NETTY TCP CLIENT START TO CONNECT SERVER, HOST: {}, PORT: {}", host, port);
            channel = future.channel();
            channel.closeFuture().sync();
        } catch (Exception e) {
            ErrorPrintUtil.printErrorMsg(logger, e);
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        String host = "127.0.0.1";
        int port = 8080;
        String id = UUID.randomUUID().toString().replaceAll("-", "");
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("id", id);
        headerMap.put("foo", "bar");
        headerMap.put("sign", SignUtil.generateSignature(headerMap, "sign"));
        // headerMap.put("sign", "sign");
        new Thread(new NettyTcpClient(host, port, headerMap)).start();

        Map<String, String> messageMap = new HashMap<>();
        messageMap.put("foo", "bar");
        messageMap.put("bar", "barz");
        boolean notSend = true;
        while (notSend) {
            if (channel != null && connected) {
                logger.info(">>>>> START TO SEND MESSAGE: {}", messageMap);
                channel.writeAndFlush(JSON.toJSONString(messageMap));
                logger.info(">>>>> SEND MESSAGE DONE");
                notSend = false;
            }
            Thread.sleep(10);
        }
    }

}