package com.example.sample.mina;

import com.alibaba.fastjson.JSON;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MinaClient {

    private String host;

    private int port;

    private String requestId;

    public MinaClient(String host, int port, String requestId) {
        this.host = host;
        this.port = port;
        this.requestId = requestId;
    }

    public void connect() {
        final NioSocketConnector connector = new NioSocketConnector();
        connector.setConnectTimeoutMillis(30000L);
        connector.getFilterChain().addLast("logger", new LoggingFilter());
        connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new SimpleCodecFactory()));
        connector.setHandler(new MinaClientHandler(requestId));
        connector.getSessionConfig().setReceiveBufferSize(10240);
        connector.getSessionConfig().setSendBufferSize(10240);
        connector.setDefaultRemoteAddress(new InetSocketAddress(host, port));
        ConnectFuture future = connector.connect();
        future.awaitUninterruptibly();
    }

    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 6007;
        String requestId = UUID.randomUUID().toString().replaceAll("-", "");
        new MinaClient(host, port, requestId).connect();

        Map<String, String> messageMap = new HashMap<>();
        messageMap.put("REQUESTID", requestId);
        messageMap.put("foo", "bar");
        String message = JSON.toJSONString(messageMap);
        MinaClientHandler.sendMessage(requestId, message);
    }
}