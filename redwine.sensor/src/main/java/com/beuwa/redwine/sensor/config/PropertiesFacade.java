package com.beuwa.redwine.sensor.config;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;

@Default
public class PropertiesFacade {
    @Inject
    private SecretManagerDAO secretManagerDao;

    @Inject
    private PropertiesFactory propertiesFactory;

    private Properties properties;

    @PostConstruct
    public void init() {
        String secrets = secretManagerDao.retrieveSecrets();
        properties = propertiesFactory.createProperties(secrets);
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
