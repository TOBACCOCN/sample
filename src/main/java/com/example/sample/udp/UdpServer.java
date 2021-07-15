package com.example.sample.udp;

import com.example.sample.util.ErrorPrintUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(UdpServer.class);

    private static void startServer(int port) {
        new Thread(() -> {
            try {
                DatagramSocket socket = new DatagramSocket(port);
                LOGGER.info(">>>>> UDP SERVER STARTED, PORT: {}", port);
                byte[] buf = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                while (true) {
                    socket.receive(packet);
                    InetAddress address = packet.getAddress();
                    int remotePort = packet.getPort();
                    String receive = new String(buf, 0, packet.getLength());
                    LOGGER.info(">>>>> RECEIVE: [{}], REMOTE_IP: [{}], REMOTE_PORT: [{}]", receive, address.getHostAddress(), remotePort);

                    String response = "Hello, I'm udp too, timestamp: " + System.currentTimeMillis();
                    LOGGER.info(">>>> SEND: [{}]", response);
                    byte[] responseBytes = response.getBytes();
                    socket.send(new DatagramPacket(responseBytes, responseBytes.length, address, remotePort));
                }
            } catch (IOException e) {
                ErrorPrintUtil.printErrorMsg(LOGGER, e);
            }
        }).start();
    }

    public static void main(String[] args) throws IOException {
        int port = 29920;
        startServer(port);
    }

}
