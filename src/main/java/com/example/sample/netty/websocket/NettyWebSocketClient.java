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
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
public class NettyWebSocketClient implements Runnable {

    // private static Logger logger = LoggerFactory.getLogger(NettyWebSocketClient.class);

    private Channel channel;
    private Boolean channelInitialized = false;

    private String url;
    private Map<String, String> headerMap;

    public NettyWebSocketClient(String url, Map<String, String> headerMap) {
        super();
        this.url = url;
        this.headerMap = headerMap;
    }

    public Channel getChannel() {
        if (!channelInitialized) {
            synchronized (this) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    ErrorPrintUtil.printErrorMsg(log, e);
                }
            }
        }
        return channel;
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
                            WebSocketVersion.V13, null, true, defaultHttpHeaders),
                            headerMap.get("requestId"));
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
            synchronized (this) {
                channel = bootstrap.connect(host, port).sync().channel();
                this.notify();
            }
            channelInitialized = true;
            log.info(">>>>> NETTY WEBSOCKET CLIENT START TO CONNECT SERVER, HOST: [{}], PORT: [{}]", host, port);

            handler.handshakeFuture().sync();

            // 线程要阻塞住，不然会退出
            Thread.currentThread().join();
        } catch (Exception e) {
            ErrorPrintUtil.printErrorMsg(log, e);
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        String url = "wss://127.0.0.1:8080";
        Map<String, String> headerMap = new HashMap<>();
        String requestId = UUID.randomUUID().toString().replaceAll("-", "");
        headerMap.put("requestId", requestId);
        headerMap.put("foo", "bar");
        headerMap.put("sign", SignUtil.generateSignature(headerMap, "sign"));
        NettyWebSocketClient webSocketClient = new NettyWebSocketClient(url, headerMap);
        // 连接服务端
        new Thread(webSocketClient).start();

        // 服务端响应连接成功后，再发送消息
        Channel channel = webSocketClient.getChannel();
        if (WebsocketChannelManager.isConnectSuccess(requestId, channel)) {
            // 发送心跳包
            new Thread(() -> {
                JSONObject heart = new JSONObject();
                int i = 0;
                while (true) {
                    try {
                        channel.writeAndFlush(new PingWebSocketFrame());
                        heart.put("heart", i++);
                        channel.writeAndFlush(new TextWebSocketFrame(heart.toString()));
                        TimeUnit.MINUTES.sleep(5);
                        // TimeUnit.SECONDS.sleep(15);
                    } catch (Exception e) {
                        ErrorPrintUtil.printErrorMsg(log, e);
                    }
                }
            }).start();

            // 发送消息给服务端
            JSONObject message = new JSONObject();
            message.put("message", "I am netty websocket client");
            channel.writeAndFlush(new TextWebSocketFrame(message.toString()));
        }
    }

}