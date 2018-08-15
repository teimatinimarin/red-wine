package com.beuwa.redwine.sensor;

import com.beuwa.redwine.sensor.config.Properties;
import com.beuwa.redwine.sensor.config.PropertiesFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.tyrus.client.ClientManager;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;

@ClientEndpoint
public class Main {
    private static final Logger LOGGER = LogManager.getLogger();

    @OnOpen
    public void onOpen(Session session) {
        LOGGER.info("Connected. SessionId={}", session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        LOGGER.info(message);
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        LOGGER.info("Closed.");
    }

    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(1);
        Properties properties = PropertiesFacade.retrieveProperties();

        ClientManager client = ClientManager.createClient();
        try {
            URI uri = Main.getEndpoint(properties);
            client.connectToServer(Main.class, uri);
            latch.await();

        } catch (DeploymentException | URISyntaxException | IOException e) {
            LOGGER.error("OOOPS! {}");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LOGGER.error("Interrupting the Thread. {}", e);
        }
    }

    private static URI getEndpoint(Properties properties) throws URISyntaxException{
        String protocol = "wss";
        String host = properties.getEndpoint();
        int port = -1;
        String path = "/realtime";
        String query = "subscribe=liquidation:XBTUSD,quote:XBTUSD,quoteBin1m:XBTUSD,quoteBin5m:XBTUSD,quoteBin1d:XBTUSD,tradeBin5m:XBTUSD";
        String auth = null;
        String fragment = null;
        return new URI(protocol, auth, host, port, path, query, fragment);
    }
}

