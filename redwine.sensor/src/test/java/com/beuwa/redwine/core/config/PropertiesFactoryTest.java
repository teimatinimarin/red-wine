package com.beuwa.redwine.core.config;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PropertiesFactoryTest {
    @InjectMocks
    PropertiesFactory propertiesFactory;

    @ParameterizedTest
    @CsvFileSource(resources = "secrets.json",delimiter = '^')
    void createProperties(String secrets) {
        Properties properties = propertiesFactory.createProperties( secrets );

        assertEquals("endpoint", properties.getEndpoint());
        assertEquals("api_key", properties.getApiKey());
        assertEquals("api_secret", properties.getApiSecret());
        assertEquals("leverage", properties.getLeverage());
        assertEquals("trading", properties.getTrading());
        assertEquals("open_email", properties.getNotifyOpen());
        assertEquals("close_email", properties.getNotifyClose());
        assertEquals("max_invest", properties.getMaxInvest());
        assertEquals(true, properties.isEventInstrumentEnable());
        assertEquals(true, properties.isEventLiquidationEnable());
        assertEquals(true, properties.isEventOrderEnable());
        assertEquals(true, properties.isEventPositionEnable());
        assertEquals(true, properties.isEventQuoteEnable());
        assertEquals(true, properties.isEventTradeEnable());
        assertEquals(true, properties.isEventWalletEnable());
    }
}