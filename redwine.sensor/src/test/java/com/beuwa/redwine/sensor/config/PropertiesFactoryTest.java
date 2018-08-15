package com.beuwa.redwine.sensor.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PropertiesFactoryTest {

    @Test
    void createProperties() {
        String secrets = "{\"endpoint\":\"endpoint\",\"trading\":\"trading\",\"open_email\":\"open_email\",\"close_email\":\"close_email\",\"api_key\":\"api_key\",\"api_secret\":\"api_secret\",\"leverage\":\"leverage\",\"max_invest\":\"max_invest\"}";
        Properties properties = PropertiesFactory.createProperties(secrets);

        assertEquals("endpoint", properties.getEndpoint());
        assertEquals("api_key", properties.getApiKey());
        assertEquals("api_secret", properties.getApiSecret());
        assertEquals("leverage", properties.getLeverage());
        assertEquals("trading", properties.getTrading());
        assertEquals("open_email", properties.getNotifyOpen());
        assertEquals("close_email", properties.getNotifyClose());
        assertEquals("max_invest", properties.getMaxInvest());
    }
}