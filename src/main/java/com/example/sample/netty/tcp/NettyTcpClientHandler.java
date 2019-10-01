package com.example.sample.netty.tcp;

import com.alibaba.fastjson.JSON;
import com.example.sample.util.ErrorPrintUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class NettyTcpClientHandler extends SimpleChannelInboundHandler<String> {

    // private static Logger logger = LoggerFactory.getLogger(NettyTcpClientHandler.class);

    private static final String MESSAGE = "message";
    private static final String MESSAGE_CONNECT_SUCCESS = "connect success";

    private Map<String, String> headerMap;

    public NettyTcpClientHandler(Map<String, String> headerMap) {
        this.headerMap = headerMap;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        log.info(">>>> RECEIVING MESSAGE: {}", msg);
        try {
            Map<String, String> map = JSON.parseObject(msg, Map.class);
            if (MESSAGE_CONNECT_SUCCESS.equals(map.get(MESSAGE))) {
                TcpChannelManager.connectSuccess(headerMap.get("requestId"), ctx.channel());
            }
        } catch (Exception e) {
            log.info(">>>>> MESSAGE IS NOT JSON");
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(JSON.toJSONString(headerMap));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ErrorPrintUtil.printErrorMsg(log, cause);
        ctx.close();
    }

}
