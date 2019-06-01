package com.example.demo.mina;

import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;

public class Client extends Thread {
	private static final Logger log = LoggerFactory.getLogger(Client.class);
	private String imei;

	public Client() {
	  
	}

	public Client(String imei) {
		this.imei = imei;
	}

	@SuppressWarnings("resource")
	public void run() {
		String host = "127.0.0.1";
		int port = 6007;
		final String imei = this.imei;
		
		final NioSocketConnector connector = new NioSocketConnector();
		connector.setConnectTimeoutMillis(30000L);
		connector.setDefaultRemoteAddress(new InetSocketAddress(host, port));
		connector.getFilterChain().addLast("logger", new LoggingFilter());
		connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new HCodecFactory()));
		connector.setHandler(new ClientHandler());
		connector.getSessionConfig().setReceiveBufferSize(10240);
		connector.getSessionConfig().setSendBufferSize(10240);
		connector.getFilterChain().addFirst("reconnection", new IoFilterAdapter() {
			public void sessionClosed(NextFilter nextFilter, IoSession ioSession) throws Exception {
			    while (true)
			    	try {
				        Thread.sleep(3000L);
				        ConnectFuture future = connector.connect();
				
				        future.awaitUninterruptibly();
				
				        IoSession session = future.getSession();
				
				        Map<String, String> map = new HashMap<String, String>();
				        map.put("key", imei);
				        session.write(JSONObject.fromObject(map).toString());
		
					if (session.isConnected()) {
					  Client.log.info("断线重连[" + connector.getDefaultRemoteAddress().getHostName() + ":"
							  + connector.getDefaultRemoteAddress().getPort() + "]成功");
					      break;
					}
				  } catch (Exception e) {
					  Client.log.info("重连服务器登录失败,3秒再连接一次:" + e.getMessage());
				  }
			}
		});
		ConnectFuture future = connector.connect();
		while (true) {
		  try {
			  future.awaitUninterruptibly();
		
			  IoSession session = future.getSession();
		
			  Map<String, String> map = new HashMap<String, String>();
			  map.put("key", imei);
			  session.write(JSONObject.fromObject(map).toString());
		
			  log.info("连接服务端" + host + ":" + port + "[成功],时间:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
			      .format(new Date()));
		  } catch (Exception e)  {
			  log.error("连接服务端" + host + ":" + port + "[失败],时间:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					  .format(new Date()) + ", 连接MSG异常,请检查MSG端口、IP是否正确,MSG服务是否启动,异常内容:" + e.getMessage(), e);
			  try {
				  Thread.sleep(5000L);
			  } catch (InterruptedException ie) {
				  log.error("连接服务端失败后，睡眠5秒发生异常！");
			  }
		}
	
		  Scanner scanner = new Scanner(System.in);
		  while (true) {
			  String string = scanner.nextLine();
			  Map<String, String> map = new HashMap<String, String>();
			  map.put("cmd", string);
			  future.getSession().write(JSONObject.fromObject(map).toString());
		  }
		}
	}

	public static void main(String[] args) throws Exception {
		for (int i = 0; i < 1; i++) {
			Thread.sleep(1000L);
			new Client(UUID.randomUUID().toString().replaceAll("-", "")).start();
		}
	}
}