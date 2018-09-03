package com.beuwa.redwine.sensor.app;

import com.beuwa.redwine.sensor.config.PropertiesFacade;
import com.beuwa.redwine.sensor.utils.Signer;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.websocket.*;
import java.time.Instant;


@ClientEndpoint
public class WebClientListener {
    @Inject
    private Logger logger;

    @Inject
    PropertiesFacade propertiesFacade;

    @Inject
    Signer signer;

    @OnOpen
    public void onOpen(Session session) throws Exception {
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

        session.getBasicRemote().sendText("{\"op\": \"subscribe\", \"args\": [\"wallet\",\"position\", \"quote:XBTUSD\"]}");
    }

    @OnMessage
    public void onMessage(String message) {
        logger.info(message);
    }

    @OnClose
    public void onClose(CloseReason closeReason) {
        logger.error("Closed.");
        logger.error(closeReason.getCloseCode().getCode());
        logger.error(closeReason.getReasonPhrase());
    }
}
