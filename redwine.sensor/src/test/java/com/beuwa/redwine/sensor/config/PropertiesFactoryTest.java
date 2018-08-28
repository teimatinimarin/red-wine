package com.beuwa.redwine.sensor.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PropertiesFactoryTest {
    @Test
    void createProperties() {
        Properties properties = PropertiesFactory.createProperties( Contants.secrets );

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