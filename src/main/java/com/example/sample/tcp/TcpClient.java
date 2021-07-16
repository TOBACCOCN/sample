package com.example.sample.tcp;

import com.example.sample.util.ErrorPrintUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

@Slf4j
public class TcpClient {

    // private static Logger logger = LoggerFactory.getLogger(TcpClient.class);

    private static Socket connectServer(String host, int port) throws IOException {
        Socket socket = new Socket(host, port);
        log.info(">>>>> CONNECT SERVER SUCCESS, HOST: [{}], PORT: [{}]", host, port);

        new Thread(() -> {
            while (!socket.isClosed()) {
                try {
                    InputStream inputStream = socket.getInputStream();
                    byte[] buf = new byte[8192];
                    int len;
                    while ((len = inputStream.read(buf)) > 0) {
                        log.info(">>>>> RECEIVE: [{}]", new String(buf, 0, len));
                    }
                } catch (Exception e) {
                    ErrorPrintUtil.printErrorMsg(log, e);
                }
            }
        }).start();
        return socket;
    }

    public static void sendMessage(Socket socket, String message) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        writer.write(message);
        writer.flush();
    }

    public static void main(String[] args) throws IOException {
        String host = "127.0.0.1";
        int port = 9898;
        Socket socket = connectServer(host, port);

        Scanner scanner = new Scanner(System.in);
        String line;
        while (StringUtils.isNotEmpty(line = scanner.nextLine())) {
            sendMessage(socket, line);
        }
        scanner.close();
        socket.close();
    }

}