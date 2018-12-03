package com.beuwa.redwine.sensor.app;

import com.beuwa.redwine.core.config.PropertiesFacade;
import com.beuwa.redwine.core.events.BootEvent;
import org.apache.logging.log4j.Logger;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.WebSocket;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

public class Initializer {
    @Inject
    private Logger logger;

    @Inject
    private PropertiesFacade propertiesFacade;

    @Inject
    WebSocketClientListener listener;

    @Inject
    WebSocket.Builder websocketBuilder;


    public void init(@Observes BootEvent bootEvent, CountDownLatch latch) {
        logger.info("Init...");
        try {
            URI uri = propertiesFacade.getWssEndpoint();
            listener.setLatch(latch);
            websocketBuilder.buildAsync(uri, listener).get();
            latch.await();
        } catch (URISyntaxException | ExecutionException e) {
            logger.error("OOOPS! {}", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Interrupting the Thread. {}", e);
        }
    }
}
