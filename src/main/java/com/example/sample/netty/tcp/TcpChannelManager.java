package com.example.sample.netty.tcp;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TcpChannelManager {

    private static Logger logger = LoggerFactory.getLogger(TcpChannelManager.class);

    private static Map<String, Channel> channelMap = new ConcurrentHashMap<>();
    private static Map<ChannelId, String> idMap = new ConcurrentHashMap<>();

    public static Map<String, Channel> getChannelMap() {
        return channelMap;
    }

    public static Map<ChannelId, String> getIdMap() {
        return idMap;
    }

    public static void registerChannel(String id, Channel channel) {
        logger.info(">>>>> REGISTER SERVER CHANNEL, ID: {}, CHANNELID: {}",
                id, channel.id());
        channelMap.put(id, channel);
        idMap.put(channel.id(), id);
    }

    public static void unregisterChannel(Channel channel) {
        String id = idMap.get(channel.id());
        if (id != null) {
            logger.info(">>>>> UNREGISTER CLIENT CHANNEL, CHANNELID: {}, ID: {}",
                    channel.id(), id);
            channelMap.remove(id);
            idMap.remove(channel.id());
        }
    }

    public static void sendMessage2Client(String id, String message) {
        Channel channel = channelMap.get(id);
        logger.info(">>>>> START TO SEND MESSAGE: {} TO CLIENT, ID: {}, CHANNELID: {}",
                message, id, channel.id());
        channel.writeAndFlush(message);
        logger.info(">>>>> SEND MESSAGE DONE");
    }
}
