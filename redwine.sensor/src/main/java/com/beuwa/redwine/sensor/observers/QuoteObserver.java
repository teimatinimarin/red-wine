package com.beuwa.redwine.sensor.observers;

import com.beuwa.redwine.core.events.business.QuoteEvent;
import org.apache.logging.log4j.Logger;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

public class QuoteObserver {
    @Inject
    Logger logger;

    public void observe(@Observes QuoteEvent event) {
        logger.debug(
                "QuoteEvent: {}",
                event.getMessage()
        );
    }
}
