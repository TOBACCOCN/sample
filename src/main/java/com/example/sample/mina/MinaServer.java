package com.example.sample.mina;

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

public class MinaServer {

    private static Logger logger = LoggerFactory.getLogger(MinaServer.class);

    private int port;

    public MinaServer(int port) {
        this.port = port;
    }

    public void run() {
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
        new MinaServer(port).run();

        long sleep = 5*1000;
        String message = "Do you have any new message? If not, I will close session!";
        new Thread(() -> {
            while (true) {
                Map<String, IoSession> sessionMap = MinaServerHandler.getSessionMap();
                sessionMap.keySet().forEach(requestId -> MinaServerHandler.sendMessage(requestId, message));
                try {
                    Thread.sleep(sleep);
                } catch (InterruptedException e) {
                    ErrorPrintUtil.printErrorMsg(logger, e);
                }
            }
        }).start();
    }
}