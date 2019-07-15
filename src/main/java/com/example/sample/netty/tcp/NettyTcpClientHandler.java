package com.example.sample.netty.tcp;

import com.alibaba.fastjson.JSON;
import com.example.sample.util.ErrorPrintUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class NettyTcpClientHandler extends SimpleChannelInboundHandler<String> {

    private static Logger logger = LoggerFactory.getLogger(NettyTcpClientHandler.class);

    private static final String MESSAGE = "message";
    private static final String MESSAGE_CONNECT_SUCCESS = "connect success";

    private Map<String, String> headerMap;

    public NettyTcpClientHandler(Map<String, String> headerMap) {
        this.headerMap = headerMap;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        logger.info(">>>> RECEIVING MESSAGE: {}", msg);
        try {
            Map<String, String> map = JSON.parseObject(msg, Map.class);
            if (MESSAGE_CONNECT_SUCCESS.equals(map.get(MESSAGE))) {
                NettyTcpClient.connected = true;
            }
        } catch (Exception e) {
            logger.info(">>>>> MESSAGE IS NOT JSON");
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        logger.info(">>>>> START TO SEND MESSAGE: {}", headerMap);
        ctx.writeAndFlush(JSON.toJSONString(headerMap));
        logger.info(">>>>> SEND MESSAGE DONE");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        NettyTcpClient.connected = false;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ErrorPrintUtil.printErrorMsg(logger, cause);
        NettyTcpClient.connected = false;
        ctx.close();
    }
}
