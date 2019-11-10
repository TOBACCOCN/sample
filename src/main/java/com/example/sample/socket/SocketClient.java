package com.example.sample.socket;

import com.example.sample.util.ErrorPrintUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

@Slf4j
public class SocketClient {

    // private static Logger logger = LoggerFactory.getLogger(SocketClient.class);

    private static Socket connectServer(String host, int port) {
        try {
            Socket socket = new Socket(host, port);
            log.info(">>>>> CONNECT SERVER SUCCESS, HOST: [{}], PORT: [{}]", host, port);

            new Thread(() -> {
                while (true) {
                    if (socket.isClosed()) {
                        break;
                    }
                    try {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        String message = reader.readLine();
                        if (message != null) {
                            log.info(">>>>> RECEIVE MSG: [{}]", message);
                        }
                    } catch (Exception e) {
                        ErrorPrintUtil.printErrorMsg(log, e);
                    }
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        ErrorPrintUtil.printErrorMsg(log, e);
                    }
                }
            }).start();

            return socket;
        } catch (Exception e) {
            ErrorPrintUtil.printErrorMsg(log, e);
            return null;
        }
    }

    public static void sendMessage(Socket socket, String message) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        writer.write(message);
        writer.newLine();
        writer.flush();
    }

    public static void main(String[] args) throws IOException {
        String host = "127.0.0.1";
        int port = 9898;
        Socket socket = connectServer(host, port);
        if (socket == null) {
            return;
        }
        Scanner scanner = new Scanner(System.in);
        String line;
        while (StringUtils.isNotEmpty(line = scanner.nextLine())) {
            sendMessage(socket, line);
        }
        scanner.close();
        socket.close();
    }

}