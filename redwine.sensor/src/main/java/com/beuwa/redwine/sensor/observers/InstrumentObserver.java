package com.beuwa.redwine.sensor.observers;

import com.beuwa.redwine.core.events.business.InstrumentEvent;
import org.apache.logging.log4j.Logger;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

public class InstrumentObserver {
    @Inject
    Logger logger;

    public void observe(@Observes InstrumentEvent event) {
        logger.debug(
                "Instrument XBTUSD: Opened: {}, TurnOver24Hrs: {}, Value24Hrs: {}, BidPrice: {}, AskPrice:{}, MarketPrice: {}",
                event.getOpenInterest(),
                event.getTurnover24H(),
                event.getValue24H(),
                event.getBidPrice(),
                event.getAskPrice(),
                event.getMarkPrice()
        );
    }
}
