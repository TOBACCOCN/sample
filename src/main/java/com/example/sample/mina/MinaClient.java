package com.example.sample.mina;

import com.alibaba.fastjson.JSONObject;
import com.example.sample.util.ErrorPrintUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
public class MinaClient {

    // private static Logger logger = LoggerFactory.getLogger(MinaClient.class);

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
        // 连接服务端
        new MinaClient(host, port, requestId).connect();

        // 发送心跳包
        JSONObject heart = new JSONObject();
        new Thread(() -> {
            int i = 0;
            while (true) {
                heart.put("heart", i++);
                MinaClientHandler.sendMessage(requestId, heart.toString());
                try {
                    // TimeUnit.MINUTES.sleep(5);
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    ErrorPrintUtil.printErrorMsg(log, e);
                }
            }
        }).start();

        // 发送消息
        JSONObject message = new JSONObject();
        message.put("requestId", requestId);
        message.put("foo", "bar");
        MinaClientHandler.sendMessage(requestId, message.toString());
    }
}