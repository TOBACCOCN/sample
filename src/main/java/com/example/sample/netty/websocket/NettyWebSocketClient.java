package com.example.sample.netty.websocket;

import com.alibaba.fastjson.JSONObject;
import com.example.sample.util.ErrorPrintUtil;
import com.example.sample.util.SignUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketClientCompressionHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

public class NettyWebSocketClient implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(NettyWebSocketClient.class);

    private static Queue<String> queue = new ConcurrentLinkedQueue<>();

    private static Channel channel;

    private String url;

    private Map<String, String> headerMap;

    public NettyWebSocketClient(String url, Map<String, String> headerMap) {
        super();
        this.url = url;
        this.headerMap = headerMap;
    }

    public void run() {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        try {
            URI uri = new URI(url);
            String host = uri.getHost();
            int port = uri.getPort();
            DefaultHttpHeaders defaultHttpHeaders = new DefaultHttpHeaders();
            headerMap.forEach(defaultHttpHeaders::add);
            NettyWebSocketClientHandler handler =
                    new NettyWebSocketClientHandler(WebSocketClientHandshakerFactory.newHandshaker(uri,
                            WebSocketVersion.V13, null, true, defaultHttpHeaders));
            bootstrap = bootstrap.group(workerGroup).option(ChannelOption.SO_KEEPALIVE, true)
                    .channel(NioSocketChannel.class)
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
            channel = bootstrap.connect(host, port).sync().channel();
            logger.info(">>>>> NETTY WEBSOCKET CLIENT START TO CONNECT SERVER, HOST: {}, PORT: {}", host, port);

            handler.handshakeFuture().sync();

            // 发送心跳包
            new Thread(() -> {
                while (true) {
                    try {
                        if (channel != null) {
                            channel.writeAndFlush(new PingWebSocketFrame());
                        }
                        Thread.sleep(1000 * 60 * 5);
                    } catch (Exception e) {
                        ErrorPrintUtil.printErrorMsg(logger, e);
                    }
                }
            }).start();
            // 发送 message
            new Thread(() -> {
                while (true) {
                    try {
                        if (channel != null) {
                            channel.writeAndFlush(new TextWebSocketFrame(queue.poll()));
                        }
                        Thread.sleep(10);
                    } catch (Exception e) {
                        ErrorPrintUtil.printErrorMsg(logger, e);
                    }
                }
            }).start();

            // 线程要阻塞住，不然会退出
            Thread.currentThread().join();
        } catch (Exception e) {
            ErrorPrintUtil.printErrorMsg(logger, e);
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        String url = "wss://127.0.0.1:8080";
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("id", UUID.randomUUID().toString().replaceAll("-", ""));
        headerMap.put("foo", "bar");
        headerMap.put("sign", SignUtil.generateSignature(headerMap, "sign"));
        new Thread(new NettyWebSocketClient(url, headerMap)).start();

        JSONObject json = new JSONObject();
        json.put("foo", "bar");
        queue.add(json.toString());
    }
}