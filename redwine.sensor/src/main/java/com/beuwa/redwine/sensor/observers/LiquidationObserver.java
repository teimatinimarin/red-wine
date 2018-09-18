package com.beuwa.redwine.sensor.observers;

import com.beuwa.redwine.core.events.LiquidationEvent;
import org.apache.logging.log4j.Logger;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

public class LiquidationObserver {
    @Inject
    Logger logger;

    public void observe(@Observes LiquidationEvent event) {
        logger.info( "Liquidation: {}", event.getMessage());
    }
}
