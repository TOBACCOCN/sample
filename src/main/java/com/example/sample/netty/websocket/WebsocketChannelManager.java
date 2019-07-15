package com.example.sample.netty.websocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WebsocketChannelManager {

    private static Map<String, Channel> channelMap = new ConcurrentHashMap<>();
    private static Map<ChannelId, String> idMap = new ConcurrentHashMap<>();

    public static Map<String, Channel> getChannelMap() {
        return channelMap;
    }

    public static Map<ChannelId, String> getIdMap() {
        return idMap;
    }

    public static void registerChannel(String id, Channel channel) {
        channelMap.put(id, channel);
        idMap.put(channel.id(), id );
    }

    public static void unregisterChannel(Channel channel) {
        ChannelId channelId = channel.id();
        String id = idMap.get(channelId);
        if (id != null) {
            channelMap.remove(id);
            idMap.remove(channelId);
        }
    }

}
