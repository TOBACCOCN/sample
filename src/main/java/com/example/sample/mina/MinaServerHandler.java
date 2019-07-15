package com.example.sample.mina;

import com.alibaba.fastjson.JSON;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MinaServerHandler extends IoHandlerAdapter {

    private static Logger logger = LoggerFactory.getLogger(MinaServerHandler.class);

    private static Map<String, IoSession> sessionMap = new ConcurrentHashMap<>();
    private static Map<Long, String> sessionIdMap = new ConcurrentHashMap<>();

    public static Map<String, IoSession> getSessionMap() {
        return sessionMap;
    }

    public void sessionCreated(IoSession session) {
        logger.info(">>>>> SESSION CREATED, SESSION_ID: {}, REMOTE: {}", session.getId(), session.getRemoteAddress().toString());
    }

    @SuppressWarnings("unchecked")
    public void messageReceived(IoSession session, Object message) {
        logger.info(">>>>> RECEIVE MSG : {}, SESSION_ID: {}", message, session.getId());
        Map<String, String> map = JSON.parseObject((String) message, Map.class);
        sessionMap.put(map.get("REQUESTID"), session);
        sessionIdMap.put(session.getId(), map.get("REQUESTID"));
    }

    public void sessionClosed(IoSession session) {
        logger.info(">>>>> SESSION CLOSED, SESSION_ID: {}, REMOTE: {}", session.getId(), session.getRemoteAddress().toString());
        sessionMap.remove(sessionIdMap.get(session.getId()));
        sessionIdMap.remove(session.getId());
    }

    public static void sendMessage(String requestId, Object message) {
        sessionMap.get(requestId).write(message);
    }

}