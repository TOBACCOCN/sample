package com.example.sample.trans.youdao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import java.io.IOException;

@ClientEndpoint
public class YoudaoWebsocket {

    private static Logger logger = LoggerFactory.getLogger(YoudaoWebsocket.class);

    private Session session;

    @OnOpen
    public void onOpen(Session session) {
        logger.info(">>>>> ON_OPEN");
        this.session = session;
    }

    @OnMessage
    public void onMessage(String message) throws IOException {
        logger.info(">>>>> ON_MESSAGE: [{}]", message);
        if (message.contains("\"errorCode\":\"304\"")) {
            onClose();
        }

    }

    @OnError
    public void onError(Throwable throwable) {
        logger.info(">>>>> WEBSOCKET ERROR OCCURRED");
    }

    @OnClose
    public void onClose() throws IOException {
        if (this.session.isOpen()) {
            this.session.close();
        }
        logger.info(">>>>> SESSION CLOSE");
    }

}