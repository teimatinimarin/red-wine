package com.beuwa.redwine.sensor.utils;

import com.beuwa.redwine.core.config.PropertiesFacade;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubscribeUtilsTest {
    @InjectMocks
    SubscribeUtils subscribeUtils;

    @Mock
    PropertiesFacade propertiesFacade;

    @Test
    void getSubscribeStringAllTrue() {
        when(propertiesFacade.isEventInstrumentEnable()).thenReturn(true);
        when(propertiesFacade.isEventLiquidationEnable()).thenReturn(true);
        when(propertiesFacade.isEventOrderEnable()).thenReturn(true);
        when(propertiesFacade.isEventPositionEnable()).thenReturn(true);
        when(propertiesFacade.isEventQuoteEnable()).thenReturn(true);
        when(propertiesFacade.isEventTradeEnable()).thenReturn(true);
        when(propertiesFacade.isEventWalletEnable()).thenReturn(true);

        String subcribe = subscribeUtils.getSubscribeString();
        assertEquals(
                "\"instrument:XBTUSD\",\"liquidation:XBTUSD\",\"order\",\"position\",\"quote:XBTUSD\",\"trade:XBTUSD\",\"wallet\"",
                subcribe
        );
    }

    @Test
    void getSubscribeStringInstrumentTrue() {
        when(propertiesFacade.isEventInstrumentEnable()).thenReturn(true);
        when(propertiesFacade.isEventLiquidationEnable()).thenReturn(false);
        when(propertiesFacade.isEventOrderEnable()).thenReturn(false);
        when(propertiesFacade.isEventPositionEnable()).thenReturn(false);
        when(propertiesFacade.isEventQuoteEnable()).thenReturn(false);
        when(propertiesFacade.isEventTradeEnable()).thenReturn(false);
        when(propertiesFacade.isEventWalletEnable()).thenReturn(false);

        String subcribe = subscribeUtils.getSubscribeString();
        assertEquals(
                "\"instrument:XBTUSD\"",
                subcribe
        );
    }

    @Test
    void getSubscribeStringLiquidationTrue() {
        when(propertiesFacade.isEventInstrumentEnable()).thenReturn(false);
        when(propertiesFacade.isEventLiquidationEnable()).thenReturn(true);
        when(propertiesFacade.isEventOrderEnable()).thenReturn(false);
        when(propertiesFacade.isEventPositionEnable()).thenReturn(false);
        when(propertiesFacade.isEventQuoteEnable()).thenReturn(false);
        when(propertiesFacade.isEventTradeEnable()).thenReturn(false);
        when(propertiesFacade.isEventWalletEnable()).thenReturn(false);

        String subcribe = subscribeUtils.getSubscribeString();
        assertEquals(
                "\"liquidation:XBTUSD\"",
                subcribe
        );
    }

    @Test
    void getSubscribeStringOrderTrue() {
        when(propertiesFacade.isEventInstrumentEnable()).thenReturn(false);
        when(propertiesFacade.isEventLiquidationEnable()).thenReturn(false);
        when(propertiesFacade.isEventOrderEnable()).thenReturn(true);
        when(propertiesFacade.isEventPositionEnable()).thenReturn(false);
        when(propertiesFacade.isEventQuoteEnable()).thenReturn(false);
        when(propertiesFacade.isEventTradeEnable()).thenReturn(false);
        when(propertiesFacade.isEventWalletEnable()).thenReturn(false);

        String subcribe = subscribeUtils.getSubscribeString();
        assertEquals(
                "\"order\"",
                subcribe
        );
    }

    @Test
    void getSubscribeStringPositionTrue() {
        when(propertiesFacade.isEventInstrumentEnable()).thenReturn(false);
        when(propertiesFacade.isEventLiquidationEnable()).thenReturn(false);
        when(propertiesFacade.isEventOrderEnable()).thenReturn(false);
        when(propertiesFacade.isEventPositionEnable()).thenReturn(true);
        when(propertiesFacade.isEventQuoteEnable()).thenReturn(false);
        when(propertiesFacade.isEventTradeEnable()).thenReturn(false);
        when(propertiesFacade.isEventWalletEnable()).thenReturn(false);

        String subcribe = subscribeUtils.getSubscribeString();
        assertEquals(
                "\"position\"",
                subcribe
        );
    }

    @Test
    void getSubscribeStringQuoteTrue() {
        when(propertiesFacade.isEventInstrumentEnable()).thenReturn(false);
        when(propertiesFacade.isEventLiquidationEnable()).thenReturn(false);
        when(propertiesFacade.isEventOrderEnable()).thenReturn(false);
        when(propertiesFacade.isEventPositionEnable()).thenReturn(false);
        when(propertiesFacade.isEventQuoteEnable()).thenReturn(true);
        when(propertiesFacade.isEventTradeEnable()).thenReturn(false);
        when(propertiesFacade.isEventWalletEnable()).thenReturn(false);

        String subcribe = subscribeUtils.getSubscribeString();
        assertEquals(
                "\"quote:XBTUSD\"",
                subcribe
        );
    }

    @Test
    void getSubscribeStringTradeTrue() {
        when(propertiesFacade.isEventInstrumentEnable()).thenReturn(false);
        when(propertiesFacade.isEventLiquidationEnable()).thenReturn(false);
        when(propertiesFacade.isEventOrderEnable()).thenReturn(false);
        when(propertiesFacade.isEventPositionEnable()).thenReturn(false);
        when(propertiesFacade.isEventQuoteEnable()).thenReturn(false);
        when(propertiesFacade.isEventTradeEnable()).thenReturn(true);
        when(propertiesFacade.isEventWalletEnable()).thenReturn(false);

        String subcribe = subscribeUtils.getSubscribeString();
        assertEquals(
                "\"trade:XBTUSD\"",
                subcribe
        );
    }

    @Test
    void getSubscribeStringWalletTrue() {
        when(propertiesFacade.isEventInstrumentEnable()).thenReturn(false);
        when(propertiesFacade.isEventLiquidationEnable()).thenReturn(false);
        when(propertiesFacade.isEventOrderEnable()).thenReturn(false);
        when(propertiesFacade.isEventPositionEnable()).thenReturn(false);
        when(propertiesFacade.isEventQuoteEnable()).thenReturn(false);
        when(propertiesFacade.isEventTradeEnable()).thenReturn(false);
        when(propertiesFacade.isEventWalletEnable()).thenReturn(true);

        String subcribe = subscribeUtils.getSubscribeString();
        assertEquals(
                "\"wallet\"",
                subcribe
        );
    }

}