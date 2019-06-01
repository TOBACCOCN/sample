package com.example.demo.mina;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Scanner;

public class Server {
	
	private static final int PORT = 6007;

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		IoAcceptor ioAcceptor = new NioSocketAcceptor();
		System.out.println("begin server....");
		ioAcceptor.getFilterChain().addLast("logger", new LoggingFilter());
		ioAcceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new HCodecFactory(Charset.forName("UTF-8"))));
		ioAcceptor.setHandler(new ServerHandler());
		ioAcceptor.getSessionConfig().setReadBufferSize(2048);
		ioAcceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
		try {
		  ioAcceptor.bind(new InetSocketAddress(PORT));
		}
		catch (IOException e) {
		  e.printStackTrace();
		}
	
		Scanner scanner = new Scanner(System.in);
		while (true) {
		  String string = scanner.nextLine();
		  SessionMap.getInstance().getSession("868739029060575").write(string);
		}
	}
}