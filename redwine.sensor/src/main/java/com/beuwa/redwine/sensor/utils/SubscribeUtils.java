package com.beuwa.redwine.sensor.utils;

import com.beuwa.redwine.sensor.config.PropertiesFacade;

import javax.inject.Inject;
import java.util.StringJoiner;

public class SubscribeUtils {
    @Inject
    PropertiesFacade propertiesFacade;

    public String getSubscribeString() {
        StringJoiner subscribe = new StringJoiner(",");

        if(propertiesFacade.isEventInstrumentEnable()) {
            subscribe.add("\"instrument:XBTUSD\"");
        }

        if(propertiesFacade.isEventLiquidationEnable()) {
            subscribe.add("\"liquidation:XBTUSD\"");
        }

        if(propertiesFacade.isEventOrderEnable()) {
            subscribe.add("\"order\"");
        }

        if(propertiesFacade.isEventPositionEnable()) {
            subscribe.add("\"position\"");
        }

        if(propertiesFacade.isEventQuoteEnable()) {
            subscribe.add("\"quote:XBTUSD\"");
        }

        if(propertiesFacade.isEventTradeEnable()) {
            subscribe.add("\"trade:XBTUSD\"");
        }

        if(propertiesFacade.isEventWalletEnable()) {
            subscribe.add("\"wallet\"");
        }

        return subscribe.toString();
    }
}
