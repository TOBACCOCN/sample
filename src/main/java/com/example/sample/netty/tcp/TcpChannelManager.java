package com.example.sample.netty.tcp;

import com.example.sample.util.ErrorPrintUtil;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TcpChannelManager {

    private static Logger logger = LoggerFactory.getLogger(TcpChannelManager.class);

    private static Map<String, Channel> requestId2ChannelMap = new ConcurrentHashMap<>();
    private static Map<Channel, String> channel2RequestIdMap = new ConcurrentHashMap<>();
    private static Map<String, Boolean> requestId2IsConnectSuccessMap = new ConcurrentHashMap<>();

    public static void registerChannel(String requestId, Channel channel) {
        requestId2ChannelMap.put(requestId, channel);
        channel2RequestIdMap.put(channel, requestId);
    }

    public static void unregisterChannel(Channel channel) {
        String requestId = channel2RequestIdMap.get(channel);
        if (requestId != null) {
            requestId2ChannelMap.remove(requestId);
        }
        channel2RequestIdMap.remove(channel);
    }

    public static Map<String, Channel> getRequestId2ChannelMap() {
        return requestId2ChannelMap;
    }

    public static String getRequestId(Channel channel) {
        return channel2RequestIdMap.get(channel);
    }

    public static Channel getChannel(String requestId) {
        return requestId2ChannelMap.get(requestId);
    }

    public static void connectSuccess(String requestId, Channel channel) {
        synchronized (channel) {
            requestId2IsConnectSuccessMap.put(requestId, true);
            channel.notify();
        }
    }

    public static Boolean isConnectSuccess(String requestId, Channel channel) {
        synchronized (channel) {
            try {
                channel.wait();
            } catch (InterruptedException e) {
                ErrorPrintUtil.printErrorMsg(logger, e);
            }
        }
        return requestId2IsConnectSuccessMap.remove(requestId);
    }

}
