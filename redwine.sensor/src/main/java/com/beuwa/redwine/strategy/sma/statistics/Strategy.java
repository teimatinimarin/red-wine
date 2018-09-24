package com.beuwa.redwine.strategy.sma.statistics;

import com.beuwa.redwine.strategy.sma.events.*;
import com.beuwa.redwine.strategy.sma.utils.Fibonacci;
import org.apache.logging.log4j.Logger;

import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

public class Strategy {
    @Inject
    Logger logger;

    @Inject
    Statistics statistics;

    @Inject
    private Event<StatisticsEvent> event;

    public void processBidPricePublishedEvent(@Observes BidPricePublishedEvent bidPricePublishedEvent) {
        logger.info(
                "BidPrice Published: {}",
                bidPricePublishedEvent.getPrice()
        );
        statistics.setBid(bidPricePublishedEvent.getPrice());
    }

    public void processAskPricePublishedEvent(@Observes AskPricePublishedEvent askPricePublishedEvent) {
        logger.info(
                "AskPrice Published: {}",
                askPricePublishedEvent.getPrice()
        );
        statistics.setAsk(askPricePublishedEvent.getPrice());
    }

    public void processMarkPricePublishedEvent(@Observes MarketPricePublishedEvent marketPricePublishedEvent) {
        logger.info(
                "MarkPrice Published: {}",
                marketPricePublishedEvent.getPrice()
        );

        Statistics.MIN_MAX_STATUS status = statistics.addMarkPrice(marketPricePublishedEvent.getEpoch(), marketPricePublishedEvent.getPrice());
        switch (status) {
            case EXISTING:
                break;
            case NEW_MAX:
                NewMaxEvent newMaxEvent = new NewMaxEvent();
                event.fire(newMaxEvent);
                break;
            case NEW_MIN:
                NewMinEvent newMinEvent = new NewMinEvent();
                event.fire(newMinEvent);
                break;
            case WARMING_UP:
                break;
        }
    }

    public void processNewMaxEvent(@Observes NewMaxEvent newMaxEvent) {
        logger.info(
                "NEW MAX, At: {}, SMA: {}, Current Price: {} SMA Max: {}, SMA Min: {}, Size: {}, Bid: {}, Ask: {}",
                statistics.getLastTickTimestamp(),
                statistics.getSma(),
                statistics.getPriceCurrent(),
                statistics.getMax(),
                statistics.getMin(),
                statistics.size(),
                statistics.getBid(),
                statistics.getAsk()
        );

        if( isTargetRangeReached() ) {
            logger.info("Target Range reached!");
        }
    }

    public void processNewMinEvent(@Observes NewMinEvent newMinEvent) {
        logger.info(
                "NEW MIN, At: {}, SMA: {}, Current Price: {} SMA Max: {}, SMA Min: {}, Size: {}, Bid: {}, Ask: {}",
                statistics.getLastTickTimestamp(),
                statistics.getSma(),
                statistics.getPriceCurrent(),
                statistics.getMax(),
                statistics.getMin(),
                statistics.size(),
                statistics.getBid(),
                statistics.getAsk()
        );

        if( isTargetRangeReached() ) {
            logger.info("Target Range reached!");
        }
    }

    private boolean isTargetRangeReached() {
        boolean reached = false;

        var sma = statistics.getSma();
        var max = statistics.getMax();
        var min = statistics.getMin();

        var range = max - min;
        var targetRange = Math.round(sma * Fibonacci.FIBONACCI_008.getPerMille() / 1000f);

        if(range >= targetRange) {
            reached = true;
        }

        return reached;
    }
}
