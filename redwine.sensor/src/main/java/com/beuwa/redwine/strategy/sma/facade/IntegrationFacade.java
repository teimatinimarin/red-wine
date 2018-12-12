package com.beuwa.redwine.strategy.sma.facade;

import com.beuwa.redwine.core.config.PropertiesFacade;
import com.beuwa.redwine.strategy.sma.dao.BrokerDao;
import com.beuwa.redwine.strategy.sma.events.*;
import com.beuwa.redwine.strategy.sma.events.broker.NewMaxTargetReachedEvent;
import com.beuwa.redwine.strategy.sma.events.broker.NewMinTargetReachedEvent;
import com.beuwa.redwine.strategy.sma.statistics.Statistics;
import com.beuwa.redwine.strategy.sma.constants.Fibonacci;
import com.beuwa.redwine.strategy.sma.utils.Round;
import com.beuwa.redwine.strategy.sma.utils.OrderQuantityCalculator;
import org.apache.logging.log4j.Logger;

import javax.enterprise.event.Event;
import javax.inject.Inject;

public abstract class IntegrationFacade {
    protected static final String SELL = "Sell";
    protected static final String BUY = "Buy";

    @Inject
    private Logger logger;

    @Inject
    protected PropertiesFacade propertiesFacade;

    @Inject
    protected BrokerDao brokerDao;

    @Inject
    protected Statistics statistics;

    @Inject
    protected OrderQuantityCalculator orderQuantityCalculator;

    @Inject
    protected Round round;

    @Inject
    protected Event<BrokerEvent> brokerEvent;

    protected long ask;
    protected double askPrice;
    protected long bid;
    protected double bidPrice;
    protected long range;
    protected long entryBounce;
    protected long takeProfitBounce;

    protected long trigger;
    protected double triggerPrice;
    protected double orderQty;
    protected long takeProfit;
    protected double takeProfitPrice;
    protected long stopLoss;
    protected double stopLossPrice;

    public void init() {
        ask = statistics.getAsk();
        askPrice = round.toNear50Cents(ask);
        bid = statistics.getBid();
        bidPrice = round.toNear50Cents(bid);
        range = statistics.getRange();
        entryBounce = Math.round( range * Fibonacci.FIBONACCI_090 / 1000f );
        takeProfitBounce = Math.round( range * Fibonacci.FIBONACCI_382 / 1000f );
    }

    public abstract void processNewMaxTargetReach(NewMaxTargetReachedEvent newMaxTargetReachedEvent);
    public abstract void processNewMinTargetReach(NewMinTargetReachedEvent newMinTargetReachedEvent);

    protected void calculateBouncingUp() {
        init();
        trigger = ( ask + entryBounce );
        triggerPrice = round.toNear50Cents(trigger); // Trigger Price
        orderQty = orderQuantityCalculator.contractsToInvest();
        takeProfit = ask + takeProfitBounce;
        takeProfitPrice = round.toNear50Cents(takeProfit); // Take Profit Price
        stopLoss = ask - entryBounce;
        stopLossPrice = round.toNear50Cents( stopLoss); // Stop Loss Price

        logger.debug("Bouncing Up calculation done: ask: {}, bid:{}, range: {}, entryBounce: {}, takeProfitBounce: {}, trigger: {}, triggerPrice:{}, simpleOtderQty: {}, takeProfit: {}, takeProfitPrice:{}, stopLoss: {}, stopLossPrice: {}",
                ask,
                bid,
                range,
                entryBounce,
                takeProfitBounce,
                trigger,
                triggerPrice,
                orderQty,
                takeProfit,
                takeProfitPrice,
                stopLoss,
                stopLossPrice
        );
    }

    protected void calculateBouncingDown() {
        init();
        trigger = ( bid - entryBounce );
        triggerPrice = round.toNear50Cents(trigger); // Trigger Price
        orderQty = orderQuantityCalculator.contractsToInvest();
        takeProfit = bid - takeProfitBounce;
        takeProfitPrice = round.toNear50Cents(takeProfit); // Take Profit Price
        stopLoss = bid + entryBounce;
        stopLossPrice = round.toNear50Cents(stopLoss); // Stop Loss Price

        logger.debug("Bouncing Down calculation done: ask: {}, bid:{}, range: {}, entryBounce: {}, takeProfitBounce: {}, trigger: {}, triggerPrice:{}, simpleOtderQty: {}, takeProfit: {}, takeProfitPrice:{}, stopLoss: {}, stopLossPrice: {}",
                ask,
                bid,
                range,
                entryBounce,
                takeProfitBounce,
                trigger,
                triggerPrice,
                orderQty,
                takeProfit,
                takeProfitPrice,
                stopLoss,
                stopLossPrice
        );
    }
}
