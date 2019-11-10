package com.example.sample.netty.tcp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.sample.util.ErrorPrintUtil;
import com.example.sample.util.SignUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Map;

@Slf4j
public class NettyTcpServerHandler extends SimpleChannelInboundHandler<String> {

    // private static Logger logger = LoggerFactory.getLogger(NettyTcpServerHandler.class);

    private static final String REQUEST_ID = "requestId";
    private static final String SIGN = "sign";

    @Override
    @SuppressWarnings("unchecked")
    public void channelRead0(ChannelHandlerContext ctx, String msg) {
        log.info(">>>>> RECEIVING MESSAGE: [{}]", msg);
        Channel channel = ctx.channel();
        if (TcpChannelManager.getRequestId(channel) == null) {
            try {
                Map<String, String> map = JSON.parseObject(msg, Map.class);
                if (SignUtil.checkSign(map, SIGN)) {
                    TcpChannelManager.registerChannel(map.get(REQUEST_ID), ctx.channel());
                    JSONObject json = new JSONObject();
                    json.put("message", "connect success");
                    channel.writeAndFlush(json.toString());
                } else {
                    log.info(">>>>> INVALID SIGN");
                    JSONObject json = new JSONObject();
                    json.put("message", "invalid sign");
                    channel.writeAndFlush(json.toString());
                    channel.close();
                }
            } catch (Exception e) {
                ErrorPrintUtil.printErrorMsg(log, e);
            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        log.info(">>>>> CLIENT CONNECTED, CHANNELID:[{}], ADDRESS: [{}]",
                ctx.channel().id(), ctx.channel().remoteAddress());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        TcpChannelManager.unregisterChannel(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        if (cause instanceof IOException) {
            log.info(">>>>> CLIENT CLOSED");
        } else {
            ErrorPrintUtil.printErrorMsg(log, cause);
        }

        Channel channel = ctx.channel();
        if (channel != null) {
            TcpChannelManager.unregisterChannel(channel);
            channel.close();
        }
    }

}
