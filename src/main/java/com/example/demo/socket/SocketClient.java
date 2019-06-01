package com.example.demo.socket;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketClient {
	Socket socket;
	BufferedReader in;
	PrintWriter out;

	public SocketClient() {
		try {
		  System.out.println("尝试连接服务器ip端口 192.168.16.128:11919");
		  this.socket = new Socket("192.168.16.128", 11919);
		  System.out.println("客户端已经成功连接至服务器");
	
		  String ipRequest = "{123456789012345678}{127.0.0.1}{1234}";
		  String requestLength = String.format("%04d", new Object[] { Integer.valueOf(ipRequest.length()) });
		  ipRequest = requestLength + ipRequest;
		  System.out.println("发送给服务端的信息是:" + ipRequest);
		  InputStream in_withcode = new ByteArrayInputStream(ipRequest.getBytes());
		  BufferedReader line = new BufferedReader(new InputStreamReader(in_withcode));
		  this.out = new PrintWriter(this.socket.getOutputStream(), true);
		  this.out.println(line.readLine());
	
		  this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		  System.out.println(this.in.readLine());
		  this.out.close();
		  this.in.close();
		  this.socket.close();
		} catch (UnknownHostException e) {
		  e.printStackTrace();
		} catch (IOException e) {
		  e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new SocketClient();
	}
}