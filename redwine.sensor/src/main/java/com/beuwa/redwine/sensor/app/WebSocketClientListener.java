package com.beuwa.redwine.sensor.app;

import com.beuwa.redwine.core.config.PropertiesFacade;
import com.beuwa.redwine.core.utils.Signer;
import com.beuwa.redwine.core.utils.SubscribeUtils;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.io.UnsupportedEncodingException;
import java.net.http.WebSocket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CountDownLatch;

public class WebSocketClientListener implements WebSocket.Listener {
    @Inject
    private Logger logger;

    @Inject
    private PropertiesFacade propertiesFacade;

    @Inject
    private Signer signer;

    @Inject
    private MessageProcessor messageProcessor;

    @Inject
    private SubscribeUtils subscribeUtils;

    private CountDownLatch latch;
    private boolean multiPart = false;
    private StringBuilder parts = new StringBuilder();

    public void setLatch(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void onOpen(WebSocket webSocket) {
        webSocket.request(1);
        logger.info("Connected");
        String key = propertiesFacade.getApiKey();

        long epoch = Instant.now().getEpochSecond();
        long expires = epoch + 10;

        String signature = null;
        try {
            signature = signer.sign("GET", "/realtime",expires, "");
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeyException e) {
            logger.error(e.getMessage());
        }

        String auth = String.format(
                "{\"op\": \"authKeyExpires\", \"args\": [\"%s\",%d,\"%s\"]}",
                key,
                expires,
                signature);
         webSocket.sendText(auth, true);

        String subscribeTemplate = "{\"op\": \"subscribe\", \"args\": [%s]}";
        String subscribe = String.format(subscribeTemplate, subscribeUtils.getSubscribeString());
        webSocket.sendText(subscribe, true);
    }

    @Override
    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
        webSocket.request(1);
        if(!last) {
            multiPart = true;
            parts.append(data.toString());
        }
        if(last) {
            if(multiPart) {
                parts.append(data.toString());
                messageProcessor.process(parts.toString());
                parts = new StringBuilder();
                multiPart = false;
            } else {
                messageProcessor.process(data.toString());
            }
        }

        return null;
    }

    @Override
    public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
        logger.error("Closed: {}", statusCode);
        logger.error("Cause: {}", reason);
        webSocket.sendClose(statusCode, reason);
        latch.countDown();
        return null;
    }

    @Override
    public void onError(WebSocket webSocket, Throwable error) {
        logger.error(error.getClass());
        logger.error(error.toString());
        logger.error("onError Message: " + error.getMessage());
        logger.error("onError Message: {}", error.getMessage());
        logger.error("onError Cause: " + error.getCause());
        logger.error("onError Cause: {}", error.getCause());
        logger.error("onError input Closed?: {}", webSocket.isInputClosed());
        logger.error("onError output Closed?: {}", webSocket.isOutputClosed());
        error.printStackTrace();
        webSocket.abort();
        latch.countDown();
    }
}
