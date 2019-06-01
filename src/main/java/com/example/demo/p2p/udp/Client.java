package com.example.demo.p2p.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Client {
    public static void main(String[] args)
            throws IOException {
        System.out.println("CLIENT STARTED !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

//        Scanner scanner = new Scanner(System.in);

//        System.out.print("nickname:");
//        String nickname = scanner.next();
        String nickname = "zyh";

//        System.out.print("server IP:");
//        String ip = scanner.next();
//        String ip = "120.79.92.137";
        String ip = "104.215.191.58";

//        System.out.print("server port:");
//        int port = scanner.nextInt();
        int port = 8888;

        DatagramSocket ds = new DatagramSocket();

        String loginStr = "LOGIN|" + nickname;

        DatagramPacket lp = new DatagramPacket(loginStr.getBytes(), loginStr
                .length(), InetAddress.getByName(ip), port);

        ds.send(lp);

        System.out.println("client port: " + ds.getLocalPort());
        System.out.println("SENDING######" + loginStr + " -----> " + ip + "|" + port);

        String heartStr = "HEART|" + nickname +
                "'s Heart Package";
        DatagramPacket hp = new DatagramPacket(heartStr.getBytes(), heartStr
                .length(), InetAddress.getByName(ip), port);
        new Thread(new HeartThread(ds, hp)).start();

        byte[] buf = new byte[1024];
        DatagramPacket rp = new DatagramPacket(buf, 1024);
        boolean isEnd = false;
        while (!isEnd) {
            ds.receive(rp);

            String content = new String(rp.getData(), 0, rp.getLength());
            String rip = rp.getAddress().getHostAddress();
            int rport = rp.getPort();

            System.out.println("RECEIVED#####" + rip + ":" + rport + " >>>>> " + content);
            if (content.startsWith("LIST_ONLINE")) {
                dealListOnline(ds, rp, content);
            } else if (content.startsWith("PUNCH_HOLE_TO")) {
                dealPunchTo(ds, rp, content);
            } else if (content.startsWith("CAN_P2P_TO")) {
                firtTimeConnectP2P(ds, rp, content);
            } else if (!content.startsWith("HELLO_MYP2P_FRIEND")) {
                content.startsWith("P2P_MESSAGE");
            }
        }
        ds.close();
    }

    //③
    private static void firtTimeConnectP2P(DatagramSocket ds, DatagramPacket rp, String content)
            throws IOException {
        String[] clientInfo = StringUtil.splitString(content,
                "|");

        String ip = clientInfo[1];
        int port = Integer.parseInt(clientInfo[2]);

//        Scanner scanner = new Scanner(System.in);
//        while (true) {

//            String send = scanner.nextLine();

        String send = "I am java client";
            DatagramPacket p2 = new DatagramPacket(send.getBytes(),
                    send.getBytes().length, InetAddress.getByName(ip), port);

            ds.send(p2);
        System.out.println("SENDING######"+ send + " -----> " + ip + "|" + port);
//        }
    }

    //②
    private static void dealPunchTo(DatagramSocket ds, DatagramPacket rp, String content)
            throws IOException {
        String[] clientInfo = StringUtil.splitString(content,
                "|");

        String ip = clientInfo[1];
        int port = Integer.parseInt(clientInfo[2]);

        String send = "HELLO_MYP2P_FRIEND|打洞";
        DatagramPacket p2 = new DatagramPacket(send.getBytes(),
                send.getBytes().length, InetAddress.getByName(ip), port);

        ds.send(p2);
//        ds.send(p2);
//        ds.send(p2);
        System.out.println("SENDING######"+ send + " -----> " + ip + "|" + port);

        send = "SUCCESS_HOLE_TO|" + ip +
                "|" + port;
        DatagramPacket p3 = new DatagramPacket(send.getBytes(),
                send.getBytes().length, rp.getAddress(), rp.getPort());
        ds.send(p3);
        System.out.println("SENDING######"+ send + " -----> " + rp.getAddress() + "|" + rp.getPort());
    }

    //①
    private static void dealListOnline(DatagramSocket ds, DatagramPacket rp, String content)
            throws IOException {
        System.out.print("和谁连接(192.168.0.2|1000|xwz),请输入(xxx不连接):");
        Scanner s = new Scanner(System.in);

        String input = s.next();
        s.close();
        if (input.equalsIgnoreCase("xxx")) {
            return;
        }
        input = "WANT_TO_CONNECT|" + input;
        DatagramPacket p = new DatagramPacket(input.getBytes(), input
                .getBytes().length, rp.getAddress(), rp.getPort());

        ds.send(p);
        System.out.println("SENDING######" + input + " -----> " + rp.getAddress() + "|" + rp.getPort());
    }
}
