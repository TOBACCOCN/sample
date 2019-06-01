package com.example.demo.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
	private ServerSocket ss;
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;

	public SocketServer() {
		try {
		  this.ss = new ServerSocket(11911);
		  while (true) {
			this.socket = this.ss.accept();
	
			String remoteIP = this.socket.getInetAddress().getHostAddress();
			String remotePort = ":" + this.socket.getLocalPort();
			System.out.println("客户端请求IP为 :" + remoteIP + remotePort);
	
			this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			String line = this.in.readLine();
			System.out.println("客户端的请求信息 :" + line);
	
			this.out = new PrintWriter(this.socket.getOutputStream(), true);
			this.out.println("服务端已收到请求信息!");
			this.out.close();
			this.in.close();
			this.socket.close();
		  }
		} catch (IOException e) {
		  e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new SocketServer();
	}
}