package com.beuwa.redwine.sensor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;


@ClientEndpoint
public class WebClientListener {
    private static final Logger LOGGER = LogManager.getLogger();

    @OnOpen
    public void onOpen() {
        LOGGER.info("Connected");
    }

    @OnMessage
    public void onMessage(String message) {
        LOGGER.info(message);
    }

    @OnClose
    public void onClose() {
        LOGGER.info("Closed.");
    }
}
