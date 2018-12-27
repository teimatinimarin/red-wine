package com.beuwa.redwine.sensor.observers;

import com.beuwa.redwine.core.events.business.PositionEvent;
import org.apache.logging.log4j.Logger;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

public class PositionObserver {
    @Inject
    Logger logger;

    public void observe(@Observes PositionEvent event) {
        logger.debug(
                "PositionEvent: {}",
                event.getMessage()
        );
    }
}
