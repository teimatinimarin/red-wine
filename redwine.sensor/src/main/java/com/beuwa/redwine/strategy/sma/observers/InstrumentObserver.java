package com.beuwa.redwine.strategy.sma.observers;

import com.beuwa.redwine.core.events.InstrumentEvent;
import com.beuwa.redwine.strategy.sma.events.SMAEvent;
import com.beuwa.redwine.strategy.sma.events.StatisticsEvent;
import com.beuwa.redwine.strategy.sma.statistics.Statistics;
import org.apache.logging.log4j.Logger;

import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

public class InstrumentObserver {
    @Inject
    Logger logger;

    @Inject
    Statistics statistics;

    @Inject
    private Event<StatisticsEvent> event;

    public void observe(@Observes InstrumentEvent instrumentEvent) {
        logger.debug(
                "Instrument XBTUSD: Opened: {}, TurnOver24Hrs: {}, Value24Hrs: {}, BidPrice: {}, AskPrice:{}, MarketPrice: {}",
                instrumentEvent.getOpenInterest(),
                instrumentEvent.getTurnover24H(),
                instrumentEvent.getValue24H(),
                instrumentEvent.getBidPrice(),
                instrumentEvent.getAskPrice(),
                instrumentEvent.getMarkPrice()
        );

        if( statistics.getSmaMarkPrice().put(instrumentEvent.getEpoch(), instrumentEvent.getMarkPrice()) ) {
            SMAEvent smaEvent = new SMAEvent(statistics.getSmaMarkPrice());
            event.fire(smaEvent);
        }
    }
}
