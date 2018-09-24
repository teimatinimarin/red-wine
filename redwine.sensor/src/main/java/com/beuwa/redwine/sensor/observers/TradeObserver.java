package com.beuwa.redwine.sensor.observers;

import com.beuwa.redwine.core.events.TradeEvent;
import org.apache.logging.log4j.Logger;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

public class TradeObserver {
    @Inject
    Logger logger;

    public void observe(@Observes TradeEvent event) {
        logger.info(
                "Trade: {} at {}, GrossValue: {}, ForeignNotional: {}",
                event.getSide(),
                event.getPrice(),
                event.getGrossValue(),
                event.getForeignNotional()
        );
    }
}