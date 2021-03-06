package com.beuwa.redwine.strategy.sma.facade;

import com.beuwa.redwine.core.events.business.QuoteEvent;
import com.beuwa.redwine.strategy.sma.events.broker.*;
import com.beuwa.redwine.strategy.sma.status.TrackingOrderStatus;
import org.apache.logging.log4j.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class TrackingFacade extends IntegrationFacade {
    @Inject
    private Logger logger;

    @Inject
    protected TrackingOrderStatus orderStatus;

    @Override
    public void processNewMaxTargetReach(@Observes NewMaxTargetReachedEvent newMaxTargetReachedEvent) {
        if(!propertiesFacade.isRedwineTrackingOn()) {
            return;
        }

        if(orderStatus.isEmpty()) {
            logger.debug("Fire TrackShortEntryEvent.");
            brokerEvent.fire(new TrackShortEntryEvent());
        } else if(orderStatus.isAccepted()) {
            logger.debug("Fire TrackMoveShortEntryEvent.");
            brokerEvent.fire(new TrackMoveShortEntryEvent());
        }
    }

    @Override
    public void processNewMinTargetReach(@Observes NewMinTargetReachedEvent newMinTargetReachedEvent) {
        if(!propertiesFacade.isRedwineTrackingOn()) {
            return;
        }

        if(orderStatus.isEmpty()) {
            logger.debug("Fire TrackLongEntryEvent.");
            brokerEvent.fire(new TrackLongEntryEvent());

        } else if(orderStatus.isAccepted()) {
            logger.debug("Fire TrackMoveLongEntryEvent.");
            brokerEvent.fire(new TrackMoveLongEntryEvent());
        }
    }

    public void longOrder(@Observes TrackLongEntryEvent trackLongEntryEvent) {
        calculateBouncingUp();
        orderStatus.accepted();
    }

    public void shorOrder(@Observes TrackShortEntryEvent trackShortEntryEvent) {
        calculateBouncingDown();
        orderStatus.accepted();
    }

    public void moveLongOrder(@Observes TrackMoveLongEntryEvent trackMoveLongEntryEvent) {
        calculateBouncingUp();
    }

    public void moveShortOrder(@Observes TrackMoveShortEntryEvent trackMoveShortEntryEvent) {
        calculateBouncingDown();
    }

    public void processQuoteEvent(@Observes QuoteEvent event) {
        if(!propertiesFacade.isRedwineTrackingOn()) {
            return;
        }

        if(!orderStatus.isEmpty()) {
            init();
        }

        if(orderStatus.isAccepted()) {
            if(side.compareTo(BUY) == 0) {
                logger.info(
                        "Tracking - Buy. bidPrice {} >= trigger {}",
                        bidPrice,
                        triggerPrice
                );
                if(bidPrice>=triggerPrice) {
                    orderStatus.filled();
                    // TODO send sns
                }
            } else if(side.compareTo(SELL) == 0) {
                logger.info(
                        "Tracking - Sell. bidPrice {} <= trigger {}",
                        askPrice,
                        triggerPrice
                );
                if(askPrice<=triggerPrice) {
                    orderStatus.filled();
                    // TODO send sns
                }
            }
        }
        else if(orderStatus.isFilled()) {
            if(side.compareTo(BUY) == 0) {
                logger.info(
                        "Tracking - Buy. bidPrice {} >= takeProfit {}. bidPrice {} < stopLoss {}",
                        bidPrice,
                        takeProfitPrice,
                        bidPrice,
                        stopLossPrice
                );
                if(bidPrice>=takeProfitPrice) {
                    logger.info("Tracking - CLOSED OK!");
                    orderStatus.reset();
                    // TODO send sns
                } else if(bidPrice<=stopLossPrice) {
                    logger.info("Tracking - CLOSED BAD!");
                    orderStatus.reset();
                    // TODO send sns
                }

            } else if(side.compareTo(SELL) == 0) {
                logger.info(
                        "Tracking - Sell. askPrice {} <= takeProfit {}. askPrice {} > stopLoss {}",
                        askPrice,
                        takeProfitPrice,
                        askPrice,
                        stopLossPrice
                );
                if(askPrice<=takeProfitPrice) {
                    logger.info("Tracking - CLOSED");
                    orderStatus.reset();
                    // TODO send sns
                } else if(askPrice>=stopLossPrice) {
                    logger.info("Tracking - CLOSED BAD!");
                    orderStatus.reset();
                    // TODO send sns
                }
            }
        }
    }
}

