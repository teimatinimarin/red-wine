package com.beuwa.redwine.sensor.observers;

import com.beuwa.redwine.core.events.OrderEvent;
import org.apache.logging.log4j.Logger;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

public class OrderObserver {
    @Inject
    Logger logger;

    public void observe(@Observes OrderEvent event) {
        logger.info( "Order: {}", event.getMessage());
    }
}
