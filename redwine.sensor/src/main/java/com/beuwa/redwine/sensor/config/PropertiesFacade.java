package com.beuwa.redwine.sensor.config;

import java.net.URI;
import java.net.URISyntaxException;

public class PropertiesFacade {
    private Properties properties;

    public PropertiesFacade() {
        super();
        //String secrets = SecretManagerDAO.retrieveSecrets();
        //properties = PropertiesFactory.createProperties(secrets);
    }

    public URI buildEndpoint() throws URISyntaxException {
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
