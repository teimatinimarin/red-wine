package com.beuwa.redwine.sensor.config;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.net.URI;
import java.net.URISyntaxException;

@Default
@Singleton
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
        String query = null;
        String auth = null;
        String fragment = null;
        return new URI(protocol, auth, host, port, path, query, fragment);
    }

    public String getApiKey() {
        return properties.getApiKey();
    }

    public String getApiSecret() {
        return properties.getApiSecret();
    }

    public boolean isEventInstrumentEnable() {
        return properties.isEventInstrumentEnable();
    }

    public boolean isEventLiquidationEnable() {
        return properties.isEventLiquidationEnable();
    }

    public boolean isEventOrderEnable() {
        return properties.isEventOrderEnable();
    }

    public boolean isEventPositionEnable() {
        return properties.isEventPositionEnable();
    }

    public boolean isEventQuoteEnable() {
        return properties.isEventQuoteEnable();
    }

    public boolean isEventTradeEnable() {
        return properties.isEventTradeEnable();
    }

    public boolean isEventWalletEnable() {
        return properties.isEventWalletEnable();
    }
}
