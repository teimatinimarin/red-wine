package com.beuwa.redwine.strategy.sma.strategy;

import com.beuwa.redwine.core.config.PropertiesFacade;
import com.beuwa.redwine.core.config.SNSDao;
import com.beuwa.redwine.core.events.business.PositionEvent;
import com.beuwa.redwine.core.events.business.QuoteEvent;
import com.beuwa.redwine.core.events.business.WalletEvent;
import com.beuwa.redwine.strategy.sma.constants.Status;
import com.beuwa.redwine.strategy.sma.events.*;
import com.beuwa.redwine.strategy.sma.events.broker.NewMaxTargetReachedEvent;
import com.beuwa.redwine.strategy.sma.events.broker.NewMinTargetReachedEvent;
import com.beuwa.redwine.strategy.sma.events.filtered.MarketPriceFilteredEvent;
import com.beuwa.redwine.strategy.sma.events.statistics.NewMaxEvent;
import com.beuwa.redwine.strategy.sma.events.statistics.NewMinEvent;
import com.beuwa.redwine.strategy.sma.statistics.Statistics;
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
    protected PropertiesFacade propertiesFacade;

    @Inject
    protected SNSDao snsDao;

    @Inject
    private Event<StatisticsEvent> statisticsEvent;

    @Inject
    private Event<BrokerEvent> brokerEvent;

    public void processMarkPricePublishedEvent(@Observes MarketPriceFilteredEvent marketPricePublishedEvent) {
        logger.debug(
                "MarkPrice Published: {}",
                marketPricePublishedEvent.getPrice()
        );

        int status = statistics.addMarkPrice(marketPricePublishedEvent.getEpoch(), marketPricePublishedEvent.getPrice());
        switch (status) {
            case Status.EXISTING:
                break;
            case Status.NEW_MAX:
                NewMaxEvent newMaxEvent = new NewMaxEvent();
                statisticsEvent.fire(newMaxEvent);
                break;
            case Status.NEW_MIN:
                NewMinEvent newMinEvent = new NewMinEvent();
                statisticsEvent.fire(newMinEvent);
                break;
            case Status.WARMING_UP:
                break;
        }
    }

    public void processNewMaxEvent(@Observes NewMaxEvent newMaxSmaEvent) {
        statistics.calculateRages();
        logger.info(
                "NEW MAX, At: {}, SMA: {}, Current Price: {} SMA Max: {}, SMA Min: {}, Size: {}, Bid: {}, Ask: {}, Range: {}, TargetRange: {}",
                statistics.getLastTickTimestamp(),
                statistics.getSma(),
                statistics.getPriceCurrent(),
                statistics.getMax(),
                statistics.getMin(),
                statistics.size(),
                statistics.getBid(),
                statistics.getAsk(),
                statistics.getRange(),
                statistics.getTargetRange()
        );

        if( isTargetRangeReached() ) {
            logger.debug("Fire NewMaxTargetReached.");
            brokerEvent.fire(new NewMaxTargetReachedEvent());
        }
    }

    public void processNewMinEvent(@Observes NewMinEvent newMinSmaEvent) {
        statistics.calculateRages();
        logger.info(
                "NEW MIN, At: {}, SMA: {}, Current Price: {} SMA Max: {}, SMA Min: {}, Size: {}, Bid: {}, Ask: {}, Range: {}, TargetRange: {}",
                statistics.getLastTickTimestamp(),
                statistics.getSma(),
                statistics.getPriceCurrent(),
                statistics.getMax(),
                statistics.getMin(),
                statistics.size(),
                statistics.getBid(),
                statistics.getAsk(),
                statistics.getRange(),
                statistics.getTargetRange()
        );

        if( isTargetRangeReached() ) {
            logger.debug("Fire NewMinTargetReached.");
            brokerEvent.fire(new NewMinTargetReachedEvent());
        }
    }

    public void processWalletEvent(@Observes WalletEvent walletEvent) {
        logger.info("Wallet - Amount: {}", walletEvent.getAmount());
        statistics.setWalletBalance(walletEvent.getAmount());
        statistics.setRealisedPnl(0);
    }



    public void processQuoteEvent(@Observes QuoteEvent event) {
        statistics.setBid(event.getBidPrice());
        statistics.setBidSize(event.getBidSize());
        statistics.setAsk(event.getAskPrice());
        statistics.setAskSize(event.getAskSize());
        logger.debug(
                "Ask: {}|{}, Bid:{}|{}",
                statistics.getAsk(),
                statistics.getAskSize(),
                statistics.getBid(),
                statistics.getBidSize()
        );
    }

    private boolean isTargetRangeReached() {
        boolean reached = false;

        var range = statistics.getRange();
        var targetRange = statistics.getTargetRange();

        if(range >= targetRange) {
            reached = true;
        }

        return reached;
    }
}
