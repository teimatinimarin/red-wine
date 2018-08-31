package com.beuwa.redwine.sensor.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PropertiesFactoryTest {
    @InjectMocks
    PropertiesFactory propertiesFactory;

    @Test
    void createProperties() {
        Properties properties = propertiesFactory.createProperties( Contants.secrets );

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