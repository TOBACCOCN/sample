package com.example.sample.mina;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MinaClientHandler extends IoHandlerAdapter {

    private static Logger logger = LoggerFactory.getLogger(MinaClientHandler.class);

    private static Map<String, IoSession> requestId2SessionMap = new ConcurrentHashMap<>();
    private static Map<IoSession, String> session2RequestIdMap = new ConcurrentHashMap<>();

    private String requestId;

    public MinaClientHandler(String requestId) {
        this.requestId = requestId;
    }

    @Override
    public void sessionCreated(IoSession session) {
        requestId2SessionMap.put(requestId, session);
        session2RequestIdMap.put(session, requestId);
    }

    @Override
    public void messageReceived(IoSession session, Object message) {
        logger.info(">>>>> RECEIVING MESSAGE: {}, SESSION_ID: {}", message, session.getId());
    }

    @Override
    public void sessionClosed(IoSession session) {
        String requestId = session2RequestIdMap.get(session);
        if (requestId != null) {
            requestId2SessionMap.remove(requestId);
        }
        session2RequestIdMap.remove(session);
    }

    public static void sendMessage(String requestId, Object message) {
        IoSession session = requestId2SessionMap.get(requestId);
        if (session != null) {
            synchronized (session) {
                session.write(message);
            }
        }
    }
}