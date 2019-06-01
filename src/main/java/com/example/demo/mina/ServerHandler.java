package com.example.demo.mina;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class ServerHandler extends IoHandlerAdapter {
	private static final Log log = LogFactory.getLog(ServerHandler.class);

	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
	  
	}

	public void messageReceived(IoSession session, Object message) throws Exception {
		log.debug("服务端收到信息-------------");
	
		String key = message.toString();
		System.out.println("message :" + message.toString());
		String carPark_id = key.substring(key.indexOf("=") + 1);
		System.out.println("carPark_id :" + carPark_id);
	
		SessionMap sessionMap = SessionMap.getInstance();
		sessionMap.addSession(carPark_id, session);
	}

	public void messageSent(IoSession session, Object message) throws Exception {
		log.debug("------------服务端发消息到客户端---");
	}

	public void sessionClosed(IoSession session) throws Exception {
		log.debug("远程session关闭了一个..." + session.getRemoteAddress().toString());
	}

	public void sessionCreated(IoSession session) throws Exception {
		log.debug(session.getRemoteAddress().toString() + "----------------------create");
		System.out.println(session.getRemoteAddress().toString() + "----------------------create");
	}

	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		log.debug(session.getServiceAddress() + "IDS");
	}

	public void sessionOpened(IoSession session) throws Exception {
		log.debug("连接打开：" + session.getLocalAddress());
	}
}