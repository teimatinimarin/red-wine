package com.beuwa.redwine.sensor;

import com.beuwa.redwine.sensor.config.PropertiesFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.tyrus.client.ClientManager;

import javax.websocket.DeploymentException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;


public class Main {
    private static final Logger LOGGER = LogManager.getLogger();

    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(1);

        PropertiesFacade propertiesFacade = new PropertiesFacade();
        ClientManager client = ClientManager.createClient();
        try {
            URI uri = propertiesFacade.buildEndpoint();
            client.connectToServer(WebClientListener.class, uri);
            latch.await();
        } catch (DeploymentException | URISyntaxException | IOException e) {
            LOGGER.error("OOOPS! {}");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LOGGER.error("Interrupting the Thread. {}", e);
        }
    }
}

