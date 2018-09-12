package com.beuwa.redwine.sensor.observers;

import com.beuwa.redwine.sensor.events.PositionEvent;
import org.apache.logging.log4j.Logger;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

public class PositionObserver {
    @Inject
    Logger logger;

    public void observe(@Observes PositionEvent event) {
        logger.info( "Position: {}", event.getMessage());
    }
}
