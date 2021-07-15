package com.example.sample.udp;

import com.example.sample.util.ErrorPrintUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class UdpClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(UdpClient.class);

    private static DatagramSocket initSocket() throws IOException {
        DatagramSocket socket = new DatagramSocket();
        byte[] buf = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        new Thread(() -> {
            try {
                while (!socket.isClosed()) {
                    socket.receive(packet);
                    LOGGER.info(">>>>> RECEIVE: [{}]", new String(buf, 0, packet.getLength()));
                }
            } catch (IOException e) {
                ErrorPrintUtil.printErrorMsg(LOGGER, e);
            }
        }).start();
        return socket;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        String host = "127.0.0.1";
        int port = 29920;
        DatagramSocket socket = initSocket();

        Scanner scanner = new Scanner(System.in);
        String line;
        while (StringUtils.isNotEmpty(line = scanner.nextLine())) {
            byte[] bytes = line.getBytes();
            socket.send(new DatagramPacket(bytes, bytes.length, InetAddress.getByName(host), port));
        }
        scanner.close();
        socket.close();
    }

}
