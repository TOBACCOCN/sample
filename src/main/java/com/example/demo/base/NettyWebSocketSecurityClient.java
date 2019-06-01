/*
 * Copyright 2014 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.example.demo.base;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.net.ssl.SSLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketClientCompressionHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

/**
 * 目前已经实现在建立连接的时候同时发送http header 信息， 然后发送一个心跳包信息。
 * 
 * @author xucheng
 *
 */
public final class NettyWebSocketSecurityClient {

    private static Logger logger = LoggerFactory.getLogger(NettyWebSocketSecurityClient.class);

   
    public static void sendMessageAndClose(String url, LinkedHashMap<String, String> msgMap, Map<String, String> headerMap, long closeDelaySeconds)  throws Exception{
        //连接保持一个指定时间秒数
        sendMessage(url,null,msgMap,headerMap, closeDelaySeconds * 1000,1);
    }
    
    public static void sendMessage(String url, LinkedHashMap<String, String> msgMap, Map<String, String> headerMap) throws Exception{
        //连接保持一个小时
        sendMessage(url,null,msgMap,headerMap,60 * 60 * 1000,1);
    }
    
    public static void sendByteArrayInMultiStep(String url, byte[] byteArray, Map<String, String> headerMap, int steps) throws Exception{
        //连接保持一个小时
        sendMessage(url,byteArray,null,headerMap,60 * 60 * 1000,steps);
    }
    
    public static void sendByteArray(String url, byte[] byteArray, Map<String, String> headerMap) throws Exception{
        //连接保持一个小时
        sendMessage(url,byteArray,null,headerMap,60 * 60 * 1000,1);
    }
    

    /**
     * websocket 信息发送，支持 ws:// 方式和 wss:// 方式
     * 样例1：wss://www.abc.com:8443/intercom/server
     * 样例2：ws://www.abc.com:8443/intercom/server
     * 
     * @param url
     * @param json
     * @throws Exception
     */
    private static void sendMessage(String url,byte[] byteArray, LinkedHashMap<String, String> msgMap, Map<String, String> headerMap, long closeDelaySeconds, int steps)
            throws Exception {
        // 如果多一个斜线就会--没反应，也不报错！ wss://www.abc.com:8443//intercom/server
        URI uri = new URI(url);
        String host = uri.getHost();
        int port = uri.getPort();
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            DefaultHttpHeaders customHeaders = new DefaultHttpHeaders();
            headerMap.entrySet().forEach(e -> {
                logger.debug(e.getKey() + ":" + e.getValue());
                customHeaders.add(e.getKey(), e.getValue());
            });
            final NettyWebSocketClientHandler webSocketClientHandler = new NettyWebSocketClientHandler(
                    WebSocketClientHandshakerFactory.newHandshaker(uri, WebSocketVersion.V13, null, true,
                            customHeaders));
            b.group(group).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel ch) {
                    ChannelPipeline p = ch.pipeline();
                    if (url.toLowerCase().startsWith("wss")) {
                        p.addLast("ssl", getSslHandler(ch, host, port));
                        logger.info("initChannel_AddSSLHandlerFinished");
                    }
                    p.addLast(new HttpClientCodec());
                    p.addLast(new HttpObjectAggregator(8192));
                    p.addLast(WebSocketClientCompressionHandler.INSTANCE);
                    p.addLast(webSocketClientHandler);
                }
            });
            Channel clientSocketChannel = b.connect(uri.getHost(), port).sync().channel();
            webSocketClientHandler.handshakeFuture().sync();
            
            //等待连接建立成功
            //Thread.sleep(2000);
            
            
            if(steps == 1 && byteArray!=null) {
                ByteBuf bbContent = Unpooled.copiedBuffer(byteArray);  
                BinaryWebSocketFrame byteArrayFrame = new BinaryWebSocketFrame(bbContent);
                clientSocketChannel.writeAndFlush(byteArrayFrame);
            }

            //无法达到预期效果。
//            if(steps > 1 && byteArray!=null) {
//                ByteList bl = new ByteList();
//                bl.addByteArray(byteArray);
//                int avgSize = byteArray.length/steps ;
//                for(int i=1; i<=steps;i++) {
//                    int currentSendSize = avgSize ;
//                    //如果是最后一部分
//                    if(i==steps) {
//                        currentSendSize = bl.size();
//                        logger.debug("currentSendSize:{}",currentSendSize);
//                        clientSocketChannel.writeAndFlush(bl.getByteArray(currentSendSize));
//                        break ;
//                    }
//                    logger.debug("currentSendSize:{}",currentSendSize);
//                    clientSocketChannel.write(bl.getByteArray(currentSendSize));
//                    logger.debug("begin2sleep300ms");
//                    Thread.sleep(100);
//                    
//                }
//                clientSocketChannel.flush();
//            }
            
            if(msgMap!=null) {
                msgMap.entrySet().forEach(e -> {
                    WebSocketFrame frame3 = new TextWebSocketFrame(e.getValue());
                    clientSocketChannel.writeAndFlush(frame3);
                    try {
                        Thread.sleep(2 * 1000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                });
               
                if (msgMap.containsKey("heart")) {
                    while (true) {
                        WebSocketFrame frame2 = new TextWebSocketFrame(msgMap.get("heart"));
                        clientSocketChannel.writeAndFlush(frame2);
                        logger.info("begin2sleep 3s");
                        Thread.sleep(3 * 1000);
                    }
                }
            }
            // 不要立即退出,维持指定时间
            Thread.sleep(closeDelaySeconds * 1000);
        } finally {
            group.shutdownGracefully();
        }
    }

    private static SslHandler getSslHandler(SocketChannel ch, String host, int port) {
        SslContext sslCtx;
        try {
            sslCtx = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
            return sslCtx.newHandler(ch.alloc(), host, port);
        } catch (SSLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
