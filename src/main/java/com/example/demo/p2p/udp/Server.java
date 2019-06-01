package com.example.demo.p2p.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static List<ConnectionClientInfo> allClients = new ArrayList<>();

    private static void StartP2PServiveChanege()
            throws IOException {
        DatagramSocket ds = new DatagramSocket(8888);

        byte[] buf = new byte[1024];
        DatagramPacket p = new DatagramPacket(buf, 1024);

        boolean isEnd = false;
        while (!isEnd) {
            ds.receive(p);

            String content = new String(p.getData(), 0, p.getLength());
            String ip = p.getAddress().getHostAddress();
            int port = p.getPort();
            if (!content.startsWith("HEART")) {
                System.out.println("RECEIVED#####" + ip + ":" + port + " >>>>> " + content);
            }
            if (content.startsWith("LOGIN")) {
                dealLogin(ds, p, content);  //①
            } else if (content.startsWith("HEART")) {
                dealHeart(ds, p, content);
            } else if (content.startsWith("WANT_TO_CONNECT")) {
                notifyPunchHole(ds, p, content);    //②
            } else if (content.startsWith("SUCCESS_HOLE_TO")) {
                notifyPunchHoleSuccess(ds, p, content); //③
            } else {
                dealOther(ds, p, content);
            }
        }
        ds.close();
    }

    //①
    private static void dealLogin(DatagramSocket ds, DatagramPacket p, String content) {
        ConnectionClientInfo c = new ConnectionClientInfo();

        String[] clientLogin = StringUtil.splitString(content,
                "|");
        c.setNickname(clientLogin[1]);
        c.setIp(p.getAddress().getHostAddress());
        c.setPort(p.getPort());
        allClients.add(c);

        String listStr = "LIST_ONLINE|" +
                serialList();

        System.out.println(listStr);
        DatagramPacket p2 = new DatagramPacket(listStr.getBytes(), listStr
                .getBytes().length, p.getAddress(), p.getPort());
        try {
            ds.send(p2);
            System.out.println("SENDING######" + listStr + " -----> " + p.getAddress() + "|" + p.getPort());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String serialList() {
        String str = "";
        for (ConnectionClientInfo cif : allClients) {
            String nickname = cif.getNickname();
            String ip = cif.getIp();
            int port = cif.getPort();
            String one = ip + "," + port + "," + nickname + "|";

            str = str + one;
        }
        return str;
    }

    private static void dealHeart(DatagramSocket ds, DatagramPacket p, String content) {
        //nothing to do
    }

    //②
    private static void notifyPunchHole(DatagramSocket ds, DatagramPacket p, String content)
            throws IOException {
        String[] clientInfo = StringUtil.splitString(content,
                "|");

        String ip = clientInfo[1];
        int port = Integer.parseInt(clientInfo[2]);

        String punchToIp = p.getAddress().getHostAddress();
        int punchToPort = p.getPort();

        String send = "PUNCH_HOLE_TO|" + punchToIp +
                "|" + punchToPort;

        DatagramPacket p2 = new DatagramPacket(send.getBytes(),
                send.getBytes().length, InetAddress.getByName(ip), port);

        ds.send(p2);
        System.out.println("SENDING######" + send + "----->" + ip + "|" + port);
    }

    //③
    private static void notifyPunchHoleSuccess(DatagramSocket ds, DatagramPacket p, String content)
            throws IOException {
        String[] clientInfo = StringUtil.splitString(content,
                "|");

        String ip = clientInfo[1];
        int port = Integer.parseInt(clientInfo[2]);

        String send = "CAN_P2P_TO|" +
                p.getAddress().getHostAddress() + "|" +
                p.getPort();

        DatagramPacket p2 = new DatagramPacket(send.getBytes(),
                send.getBytes().length, InetAddress.getByName(ip), port);

        ds.send(p2);
        System.out.println("SENDING######" + send + " -----> " + ip + "|" + port);
    }

    private static void dealOther(DatagramSocket ds, DatagramPacket p, String content) {
    }

    public static void main(String[] args)
            throws IOException {
        System.out.println("SERVER STARTED !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        StartP2PServiveChanege();
    }
}
