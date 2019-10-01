package com.example.sample.socket;

import com.example.sample.util.ErrorPrintUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.UUID;

@Slf4j
public class SocketServer {

    // private static Logger logger = LoggerFactory.getLogger(SocketServer.class);

    private static void startServer(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            log.info(">>>>> SERVER STARTED, PORT: {}", port);

            new Thread(() -> {
                while (true) {
                    try {
                        Socket socket = serverSocket.accept();
                        String remoteIP = socket.getInetAddress().getHostAddress();
                        int remotePort = socket.getPort();
                        log.info(">>>>> CLIENT INCOME, IP: {}, PORT: {}", remoteIP, remotePort);

                        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                        String msg;
                        while ((msg = reader.readLine()) != null) {
                            log.info(">>>>> RECEIVING MSG: {}", msg);
                            writer.write(UUID.randomUUID().toString().replaceAll("-", ""));
                            writer.newLine();
                            writer.flush();
                        }
                        reader.close();
                        writer.close();
                    } catch (IOException e) {
                        ErrorPrintUtil.printErrorMsg(log, e);
                    }
                }
            }).start();
        } catch (IOException e) {
            ErrorPrintUtil.printErrorMsg(log, e);
        }
    }

    public static void main(String[] args) {
        int port = 9898;
        startServer(port);
    }

}