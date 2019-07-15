package com.example.sample.mina;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MinaClientHandler extends IoHandlerAdapter {

	private static Logger logger = LoggerFactory.getLogger(MinaClientHandler.class);

	private static Map<String, IoSession> sessionMap = new ConcurrentHashMap<>();
	private static Map<Long, String> sessionIdMap = new ConcurrentHashMap<>();

	private String requestId;

	public MinaClientHandler(String requestId) {
		this.requestId = requestId;
	}

	@Override
	public void sessionCreated(IoSession session)  {
		logger.info(">>>>> SESSION CREATED");
		sessionMap.put(requestId, session);
		sessionIdMap.put(session.getId(), requestId);
	}

	@Override
	public void messageReceived(IoSession session, Object message) {
		logger.info(">>>>> RECEIVE MSG: {}, SESSION_ID: {}", message, session.getId());
	}

	@Override
	public void sessionClosed(IoSession session) {
		logger.info(">>>>> SESSION CLOSED");
		sessionMap.remove(sessionIdMap.get(session.getId()));
		sessionIdMap.remove(session.getId());
	}

	public static void sendMessage(String requestId, Object message) {
		sessionMap.get(requestId).write(message);
	}
}