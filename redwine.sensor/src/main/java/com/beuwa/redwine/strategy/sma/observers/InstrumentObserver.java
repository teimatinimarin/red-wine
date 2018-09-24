package com.beuwa.redwine.strategy.sma.observers;

import com.beuwa.redwine.core.events.InstrumentEvent;
import com.beuwa.redwine.strategy.sma.events.*;
import org.apache.logging.log4j.Logger;

import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

public class InstrumentObserver {
    @Inject
    Logger logger;

    @Inject
    private Event<PricePublishedEvent> event;

    public void processIntrumentEvent(@Observes InstrumentEvent instrumentEvent) {
        long epoch = instrumentEvent.getEpoch();
        if (instrumentEvent.getMarkPrice() > 0) {
            // We are not interested in this event if Mark Price is Zero.
            event.fire(new MarketPricePublishedEvent(epoch, instrumentEvent.getMarkPrice()));
        }
        if (instrumentEvent.getBidPrice() > 0) {
            // We are not interested in this event if Mark Price is Zero.
            event.fire(new BidPricePublishedEvent(epoch, instrumentEvent.getBidPrice()));
        }
        if (instrumentEvent.getAskPrice() > 0) {
            // We are not interested in this event if Mark Price is Zero.
            event.fire(new AskPricePublishedEvent(epoch, instrumentEvent.getAskPrice()));
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
