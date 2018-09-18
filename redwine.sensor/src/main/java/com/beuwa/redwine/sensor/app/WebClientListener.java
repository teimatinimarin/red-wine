package com.beuwa.redwine.sensor.app;

import com.beuwa.redwine.core.config.PropertiesFacade;
import com.beuwa.redwine.sensor.utils.Signer;
import com.beuwa.redwine.sensor.utils.SubscribeUtils;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.websocket.*;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;


@ClientEndpoint
public class WebClientListener {
    @Inject
    private Logger logger;

    @Inject
    PropertiesFacade propertiesFacade;

    @Inject
    Signer signer;

    @Inject
    MessageProcessor messageProcessor;

    @Inject
    SubscribeUtils subscribeUtils;

    @OnOpen
    public void onOpen(Session session) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        logger.info("Connected");
        String key = propertiesFacade.getApiKey();

        long epoch = Instant.now().getEpochSecond();
        long expires = epoch + 10;

        String signature = signer.encode("GET", "/realtime",expires, "");
        String auth = String.format(
                "{\"op\": \"authKeyExpires\", \"args\": [\"%s\",%d,\"%s\"]}",
                key,
                expires,
                signature);
        session.getBasicRemote().sendText(auth);

        String subscribeTemplate = "{\"op\": \"subscribe\", \"args\": [%s]}";
        String subscribe = String.format(subscribeTemplate, subscribeUtils.getSubscribeString());
        session.getBasicRemote().sendText(subscribe);
    }

    @OnMessage
    public void onMessage(String message) {
        messageProcessor.process(message);
    }

    @OnClose
    public void onClose(CloseReason closeReason) {
        logger.error("Closed.");
        logger.error(closeReason.getCloseCode().getCode());
        logger.error(closeReason.getReasonPhrase());
    }
}
