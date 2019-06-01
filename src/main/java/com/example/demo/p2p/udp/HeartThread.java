package com.example.demo.p2p.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class HeartThread
        implements Runnable {
    private DatagramSocket ds;
    private DatagramPacket p;

    public HeartThread(DatagramSocket ds, DatagramPacket p) {
        this.ds = ds;
        this.p = p;
    }

    public void run() {
        for (; ; ) {
            try {
                this.ds.send(this.p);
                Thread.sleep(500L);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
