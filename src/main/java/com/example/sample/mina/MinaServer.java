package com.example.sample.mina;

import com.alibaba.fastjson.JSONObject;
import com.example.sample.util.ErrorPrintUtil;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MinaServer {

    private static Logger logger = LoggerFactory.getLogger(MinaServer.class);

    public static void startServer(int port) {
        IoAcceptor ioAcceptor = new NioSocketAcceptor();
        ioAcceptor.getFilterChain().addLast("logger", new LoggingFilter());
        ioAcceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new SimpleCodecFactory()));
        ioAcceptor.setHandler(new MinaServerHandler());
        ioAcceptor.getSessionConfig().setReadBufferSize(2048);
        ioAcceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
        try {
            ioAcceptor.bind(new InetSocketAddress(port));
            logger.info(">>>>> MINA SERVER STARTED, PORT: {}", port);
        } catch (IOException e) {
            ErrorPrintUtil.printErrorMsg(logger, e);
            ioAcceptor.dispose();
        }

    }

    public static void main(String[] args) {
        int port = 6007;
        // 启动服务
        startServer(port);

        // 模拟服务端主动给客户端发送消息
        new Thread(() -> {
            JSONObject message = new JSONObject();
            message.put("message", "I am mina server");
            while (true) {
                Map<String, IoSession> requestId2SessionMap = MinaServerHandler.getRequestId2SessionMap();
                if (requestId2SessionMap.size() > 0) {
                    requestId2SessionMap.keySet().forEach(requestId ->
                            MinaServerHandler.sendMessage(requestId, message.toString()));
                }
                try {
                    // TimeUnit.MINUTES.sleep(5);
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    ErrorPrintUtil.printErrorMsg(logger, e);
                }
            }
        }).start();
    }
}