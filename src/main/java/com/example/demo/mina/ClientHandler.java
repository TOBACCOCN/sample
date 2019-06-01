package com.example.demo.mina;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientHandler extends IoHandlerAdapter {
	private static final Logger log = LoggerFactory.getLogger(ClientHandler.class);

	public void messageReceived(IoSession session, Object message) throws Exception {
		log.info("------服务端返回的json数据------");
		String s = message.toString();
		log.info("------" + s + "------");
	}

	@SuppressWarnings("deprecation")
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		log.info("-客户端与服务端连接[空闲] - " + status.toString());
		System.out.println("-客户端与服务端连接[空闲] - " + status.toString());
	
		if (session != null)
		  session.close(true);
	}
}