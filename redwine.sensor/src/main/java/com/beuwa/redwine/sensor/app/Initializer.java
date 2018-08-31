package com.beuwa.redwine.sensor.app;


import com.beuwa.redwine.sensor.config.PropertiesFacade;
import com.beuwa.redwine.sensor.events.BootEvent;
import org.apache.logging.log4j.Logger;
import org.glassfish.tyrus.client.ClientManager;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.websocket.DeploymentException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;

public class Initializer {
    @Inject
    private Logger logger;

    @Inject
    private PropertiesFacade propertiesFacade;

    @Inject
    private ClientManager client;

    @Inject
    WebClientListener listener;

    public void init(@Observes BootEvent bootEvent, CountDownLatch latch) {
        logger.info("Init...");
        try {
            URI uri = propertiesFacade.buildEndpoint();
            client.connectToServer(listener, uri);
            latch.await();
        } catch (DeploymentException | URISyntaxException | IOException e) {
            logger.error("OOOPS! {}", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Interrupting the Thread. {}", e);
        }
    }
}
