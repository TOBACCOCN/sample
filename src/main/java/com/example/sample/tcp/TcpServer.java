package com.example.sample.tcp;

import com.example.sample.util.ErrorPrintUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.UUID;

@Slf4j
public class TcpServer {

    // private static Logger logger = LoggerFactory.getLogger(SocketServer.class);

    private static void startServer(int port) {
        new Thread(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(port);
                log.info(">>>>> TCP SERVER STARTED, PORT: [{}]", port);
                while (true) {
                    Socket socket = serverSocket.accept();
                    String remoteIP = socket.getInetAddress().getHostAddress();
                    int remotePort = socket.getPort();
                    log.info(">>>>> CLIENT INCOME, IP: [{}], PORT: [{}]", remoteIP, remotePort);

                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    String msg;
                    while ((msg = reader.readLine()) != null) {
                        log.info(">>>>> RECEIVE: [{}]", msg);
                        String response = "Hello, I'm TCP Server, timestamp: " + System.currentTimeMillis();
                        log.info(">>>>> SEND: [{}]", response);
                        writer.write(response);
                        writer.newLine();
                        writer.flush();
                    }
                    reader.close();
                    writer.close();
                }
            } catch (IOException e) {
                ErrorPrintUtil.printErrorMsg(log, e);
            }
        }).start();
    }

    public static void main(String[] args) {
        int port = 9898;
        startServer(port);
    }

}