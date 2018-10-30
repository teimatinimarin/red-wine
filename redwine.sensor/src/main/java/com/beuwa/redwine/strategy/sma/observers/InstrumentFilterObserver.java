package com.beuwa.redwine.strategy.sma.observers;

import com.beuwa.redwine.core.events.business.InstrumentEvent;
import com.beuwa.redwine.strategy.sma.events.*;
import com.beuwa.redwine.strategy.sma.events.filtered.MarketPriceFilteredEvent;
import org.apache.logging.log4j.Logger;

import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

public class InstrumentFilterObserver {
    @Inject
    Logger logger;

    @Inject
    private Event<PriceFilteredEvent> event;

    public void processIntrumentEvent(@Observes InstrumentEvent instrumentEvent) {
        long epoch = instrumentEvent.getEpoch();
        if (instrumentEvent.getMarkPrice() > 0) {
            // We are not interested in this event if Mark Price is Zero.
            event.fire(new MarketPriceFilteredEvent(epoch, instrumentEvent.getMarkPrice()));
        }

        logger.debug(
                "Instrument XBTUSD: Opened: {}, TurnOver24Hrs: {}, Value24Hrs: {}, BidPrice: {}, AskPrice:{}, MarketPrice: {}",
                instrumentEvent.getOpenInterest(),
                instrumentEvent.getTurnover24H(),
                instrumentEvent.getValue24H(),
                instrumentEvent.getBidPrice(),
                instrumentEvent.getAskPrice(),
                instrumentEvent.getMarkPrice()
            );
    }
}
