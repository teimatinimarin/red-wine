package com.beuwa.redwine.strategy.sma.statistics;

import com.beuwa.redwine.strategy.sma.events.SMAEvent;
import org.apache.logging.log4j.Logger;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

public class Strategy {
    @Inject
    Logger logger;

    public void processSMAEvent(@Observes SMAEvent smaEvent) {
        logger.info(
                "{} - SMA: {}, Current: {} Min: {}, Max: {}, Size: {}",
                smaEvent.getWindowMap().getDateString(),
                smaEvent.getWindowMap().getSma(),
                smaEvent.getWindowMap().getCurrent(),
                smaEvent.getWindowMap().getMin(),
                smaEvent.getWindowMap().getMax(),
                smaEvent.getWindowMap().size()
        );
    }
}
