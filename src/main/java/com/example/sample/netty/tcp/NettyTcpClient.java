package com.example.sample.netty.tcp;

import com.alibaba.fastjson.JSONObject;
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
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
public class NettyTcpClient implements Runnable {

    // private static Logger logger = LoggerFactory.getLogger(NettyTcpClient.class);

    private Channel channel;
    private static boolean channelInitialized = false;
    private String host;
    private int port;
    private Map<String, String> headerMap;

    public NettyTcpClient(String host, int port, Map<String, String> headerMap) {
        super();
        this.host = host;
        this.port = port;
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
            log.info(">>>>> NETTY TCP CLIENT START TO CONNECT SERVER, HOST: [{}], PORT: [{}]", host, port);
            synchronized (this) {
                channel = future.channel();
                this.notify();
            }
            channelInitialized = true;
            channel.closeFuture().sync();
        } catch (Exception e) {
            ErrorPrintUtil.printErrorMsg(log, e);
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        String host = "127.0.0.1";
        int port = 8080;
        Map<String, String> headerMap = new HashMap<>();
        String requestId = UUID.randomUUID().toString().replaceAll("-", "");
        headerMap.put("requestId", requestId);
        headerMap.put("foo", "bar");
        headerMap.put("sign", SignUtil.generateSignature(headerMap, "sign"));
        // headerMap.put("sign", "sign");
        NettyTcpClient tcpClient = new NettyTcpClient(host, port, headerMap);
        // 连接服务端
        new Thread(tcpClient).start();

        Channel channel = tcpClient.getChannel();
        if (TcpChannelManager.isConnectSuccess(requestId, channel)) {
            new Thread(() -> {
                // 发送心跳包
                JSONObject heart = new JSONObject();
                int i = 0;
                while (true) {
                    heart.put("heart", i++);
                    channel.writeAndFlush(heart.toString());
                    try {
                        TimeUnit.MINUTES.sleep(5);
                        // TimeUnit.SECONDS.sleep(15);
                    } catch (InterruptedException e) {
                        ErrorPrintUtil.printErrorMsg(log, e);
                    }
                }
            }).start();

            // 发送消息给服务端
            JSONObject message = new JSONObject();
            message.put("message", "I am netty tcp client");
            channel.writeAndFlush(message.toString());
        }

    }

}