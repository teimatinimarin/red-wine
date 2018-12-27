package com.beuwa.redwine.core.config;

import com.beuwa.redwine.core.config.beans.Properties;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;

@Default
@ApplicationScoped
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

    public String getHttpsEndpoint() {
        return "https://"+properties.getEndpoint();
    }

    public URI getWssEndpoint() throws URISyntaxException {
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

    public String getRedwineSns() {
        return properties.getRedwineSns();
    }

    public boolean sendOpen() {
        return "on".equalsIgnoreCase(properties.getNotifyOpen());
    }

    public boolean sendClose() {
        return "on".equalsIgnoreCase(properties.getNotifyClose());
    }

    public long getRedwineSmaPeriod() {
        return properties.getRedwineSmaPeriod();
    }

    public long getRedwineWarmupPeriod() {
        return properties.getRedwineWarmupPeriod();
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

    public String getLeverage() {
        return properties.getLeverage();
    }

    public int getMaxInvest() {
        return Integer.parseInt(properties.getMaxInvest());
    }

    public long getPercentageToInvest() {
        return Long.parseLong(properties.getPercentageToInvest());
    }

    public boolean isRedwineTradingOn() {
        return "on".equalsIgnoreCase(properties.getRedwineTrading());
    }

    public boolean isRedwineTrackingOn() {
        return "on".equalsIgnoreCase(properties.getRedwineTracking());
    }
}
