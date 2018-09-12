package com.beuwa.redwine.sensor.observers;

import com.beuwa.redwine.sensor.events.QuoteEvent;
import org.apache.logging.log4j.Logger;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

public class QuoteObserver {
    @Inject
    Logger logger;

    public void observe(@Observes QuoteEvent event) {
        logger.info(
                "Ask: {}|{}, Bid:{}|{}",
                event.getAskPrice(),
                event.getAskSize(),
                event.getBidPrice(),
                event.getBidSize()
        );
    }
}
