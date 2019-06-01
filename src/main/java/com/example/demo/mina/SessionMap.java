package com.example.demo.mina;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.session.IoSession;

public class SessionMap {
	
	private static final Log log = LogFactory.getLog(SessionMap.class);

	private static SessionMap sessionMap = null;

	private Map<String, IoSession> map = new HashMap<String, IoSession>();

	public static SessionMap getInstance() {
		log.debug("SessionMap单例获取---");
		if (sessionMap == null) {
		  sessionMap = new SessionMap();
		}
		return sessionMap;
	}

	public void addSession(String key, IoSession session) {
		log.debug("保存会话到SessionMap单例---key=" + key);
		this.map.put(key, session);
	}

	public IoSession getSession(String key) {
		log.debug("获取会话从SessionMap单例---key=" + key);
		return (IoSession)this.map.get(key);
	}

	public Map<String, IoSession> getMap() {
		return this.map;
	}

	public void sendMessage(String[] keys, Object message) {
		for (String key : keys) {
		  IoSession session = getSession(key);
	
		  log.debug("反向发送消息到客户端Session---key=" + key + "----------消息=" + message);
		  if (session == null) {
			return;
		  }
		  session.write(message);
		}
	}
}