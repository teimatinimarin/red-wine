package com.beuwa.redwine.sensor.app;

import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.websocket.ClientEndpoint;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;


@ClientEndpoint
public class WebClientListener {
    @Inject
    private Logger logger;

    @OnOpen
    public void onOpen() {
        logger.info("Connected");
    }

    @OnMessage
    public void onMessage(String message) {
        logger.info(message);
    }

    @OnClose
    public void onClose() {
        logger.info("Closed.");
    }
}
