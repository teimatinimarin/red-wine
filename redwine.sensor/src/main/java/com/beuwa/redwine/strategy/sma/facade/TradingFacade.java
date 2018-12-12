package com.beuwa.redwine.strategy.sma.facade;

import com.beuwa.redwine.core.config.SNSDao;
import com.beuwa.redwine.strategy.sma.events.broker.*;
import com.beuwa.redwine.strategy.sma.status.TradingOrderStatus;
import org.apache.logging.log4j.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class TradingFacade extends IntegrationFacade {
    @Inject
    private Logger logger;

    @Inject
    protected TradingOrderStatus orderStatus;

    @Inject
    protected SNSDao snsDao;

    @Override
    public void processNewMaxTargetReach(@Observes NewMaxTargetReachedEvent newMaxTargetReachedEvent) {
        if(!propertiesFacade.isRedwineTradingOn()) {
            return;
        }

        if(orderStatus.isEmpty()) {
            logger.debug("Fire ShortEntryEvent.");
            brokerEvent.fire(new ShortEntryEvent());
            orderStatus.sent();
        } else if(orderStatus.isAccepted()) {
            logger.debug("Fire MoveShortEntryEvent.");
            brokerEvent.fire(new MoveShortEntryEvent());
        }
    }

    @Override
    public void processNewMinTargetReach(@Observes NewMinTargetReachedEvent newMinTargetReachedEvent) {
        if(!propertiesFacade.isRedwineTradingOn()) {
            return;
        }

        if(orderStatus.isEmpty()) {
            logger.debug("Fire LongEntryEvent.");
            brokerEvent.fire(new LongEntryEvent());

        } else if(orderStatus.isAccepted()) {
            logger.debug("Fire MoveLongEntryEvent.");
            brokerEvent.fire(new MoveLongEntryEvent());
        }
    }

    public void longOrder(@Observes LongEntryEvent longEntryEvent) {
        calculateBouncingUp();

        brokerDao.setLeverage();
        logger.debug("Leverage set");

        brokerDao.createOrder(orderQty, triggerPrice, takeProfitPrice, stopLossPrice, SELL);
        logger.debug("Orders created");

        if(propertiesFacade.sendOpen()) {
            snsDao.publish("Long Opened", "Order created");
            logger.debug("SNS Message published. For Open.");
        }
    }

    public void shorOrder(@Observes ShortEntryEvent shortEntryEvent) {
        calculateBouncingDown();

        brokerDao.setLeverage();
        logger.debug("Leverage set");

        brokerDao.createOrder(orderQty, triggerPrice, takeProfitPrice, stopLossPrice, BUY);
        logger.debug("Orders created");

        if(propertiesFacade.sendOpen()) {
            snsDao.publish("Short Opened", "Order created");
            logger.debug("SNS Message published. For Open.");
        }
    }

    public void moveLongOrder(@Observes MoveLongEntryEvent moveLongEntryEvent) {
        calculateBouncingUp();

        var currentTriggerPrice = orderStatus.getParent().getStopPx();
        if(trigger < currentTriggerPrice) {
            logger.debug("Moving Orders. triggerPrice: {}, stopPx: {}", triggerPrice, currentTriggerPrice);
            brokerDao.moveOrder(
                    orderStatus.getParent().getClientOrderLinkId(),
                    triggerPrice,
                    takeProfitPrice,
                    stopLossPrice
            );
        } else {
            logger.debug("Bypassing order move.");
        }
    }

    public void moveShortOrder(@Observes MoveShortEntryEvent moveShortEntryEvent) {
        calculateBouncingDown();

        var currentTriggerPrice = orderStatus.getParent().getStopPx();
        if(trigger > currentTriggerPrice) {
            logger.debug("Moving Orders. triggerPrice: {}, stopPx: {}", triggerPrice, currentTriggerPrice);
            brokerDao.moveOrder(
                    orderStatus.getParent().getClientOrderLinkId(),
                    triggerPrice,
                    takeProfitPrice,
                    stopLossPrice
            );
        } else {
            logger.debug("Bypassing order move.");
        }
    }
}
