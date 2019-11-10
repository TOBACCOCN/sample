package com.example.sample.mina;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class MinaServerHandler extends IoHandlerAdapter {

    // private static Logger logger = LoggerFactory.getLogger(MinaServerHandler.class);

    private static Map<String, IoSession> requestId2SessionMap = new ConcurrentHashMap<>();
    private static Map<IoSession, String> session2RequestIdMap = new ConcurrentHashMap<>();

    public static Map<String, IoSession> getRequestId2SessionMap() {
        return requestId2SessionMap;
    }

    public void sessionCreated(IoSession session) {
        log.info(">>>>> SESSION CREATED, SESSION_ID: [{}], REMOTE: [{}]",
                session.getId(), session.getRemoteAddress().toString());
    }

    @SuppressWarnings("unchecked")
    public void messageReceived(IoSession session, Object message) {
        log.info(">>>>> RECEIVE MSG : [{}], SESSION_ID: [{}]", message, session.getId());
        Map<String, String> map = JSON.parseObject((String) message, Map.class);
        String requestId = map.get("requestId");
        if (requestId != null) {
            requestId2SessionMap.put(requestId, session);
            session2RequestIdMap.put(session, requestId);
        }
    }

    public void sessionClosed(IoSession session) {
        log.info(">>>>> SESSION CLOSED, SESSION_ID: [{}], REMOTE: [{}]", session.getId(), session.getRemoteAddress().toString());
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