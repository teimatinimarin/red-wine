package com.beuwa.redwine.strategy.sma.facade;

import com.beuwa.redwine.core.config.SNSDao;
import com.beuwa.redwine.core.events.business.OrderEvent;
import com.beuwa.redwine.core.events.business.PositionEvent;
import com.beuwa.redwine.strategy.sma.events.broker.*;
import com.beuwa.redwine.strategy.sma.events.filtered.OrderChangedEvent;
import com.beuwa.redwine.strategy.sma.status.Order;
import com.beuwa.redwine.strategy.sma.status.TradingOrderStatus;
import org.apache.logging.log4j.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.UUID;

import static com.beuwa.redwine.strategy.sma.constants.Orders.*;

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
            logger.debug("Trade - Fire ShortEntryEvent.");
            brokerEvent.fire(new ShortEntryEvent());
            orderStatus.sent();
        } else if(orderStatus.isAccepted()) {
            logger.debug("Trade - Fire MoveShortEntryEvent.");
            brokerEvent.fire(new MoveShortEntryEvent());
        }
    }

    @Override
    public void processNewMinTargetReach(@Observes NewMinTargetReachedEvent newMinTargetReachedEvent) {
        if(!propertiesFacade.isRedwineTradingOn()) {
            return;
        }

        if(orderStatus.isEmpty()) {
            logger.debug("Trade - Fire LongEntryEvent.");
            brokerEvent.fire(new LongEntryEvent());
            orderStatus.sent();
        } else if(orderStatus.isAccepted()) {
            logger.debug("Trade - Fire MoveLongEntryEvent.");
            brokerEvent.fire(new MoveLongEntryEvent());
        }
    }

    public void longOrder(@Observes LongEntryEvent longEntryEvent) {
        calculateBouncingUp();

        brokerDao.setLeverage();
        logger.debug("Trade - Leverage set");

        var familyOrderId = UUID.randomUUID().toString().substring(24, 36);
        orderStatus.setFamilyOrderId(familyOrderId);
        brokerDao.createEntryOrder(familyOrderId, orderQty, triggerPrice, BUY);
        logger.debug("Trade - Long Entry order created");

        if(propertiesFacade.sendOpen()) {
            snsDao.publish("Trade - Long Opened", "Order created");
            logger.debug("Trade - SNS Message published. For Open.");
        }
    }

    public void shortOrder(@Observes ShortEntryEvent shortEntryEvent) {
        calculateBouncingDown();

        brokerDao.setLeverage();
        logger.debug("Trade - Leverage set");

        var familyOrderId = UUID.randomUUID().toString().substring(24, 36);
        orderStatus.setFamilyOrderId(familyOrderId);
        brokerDao.createEntryOrder(familyOrderId, orderQty, triggerPrice, SELL);
        logger.debug("Trade - Short Entry order created");

        if(propertiesFacade.sendOpen()) {
            snsDao.publish("Trade - Short Opened", "Order created");
            logger.debug("Trade - SNS Message published. For Open.");
        }
    }

    public void moveLongOrder(@Observes MoveLongEntryEvent moveLongEntryEvent) {
        calculateBouncingUp();

        var familyOrderId = orderStatus.getFamilyOrderId();
        var clientOrderId = familyOrderId + "-" + ENTRY.name();
        Order order = orderStatus.getOrderByClientOrderId(clientOrderId);
        var orderId = order.getOrderId();
        var currentTriggerPrice = order.getStopPx();
        if(trigger < currentTriggerPrice) {
            logger.debug("Trade - Moving Long Order. NewTriggerPrice: {}, CurrentTriggerPrice: {}", trigger, currentTriggerPrice);
            brokerDao.moveEntryOrder(
                    orderId,
                    triggerPrice
            );
        } else {
            logger.debug("Trade - Bypassing order move.");
        }
    }

    public void moveShortOrder(@Observes MoveShortEntryEvent moveShortEntryEvent) {
        calculateBouncingDown();

        var familyOrderId = orderStatus.getFamilyOrderId();
        var clientOrderId = familyOrderId + "-" + ENTRY;
        Order order = orderStatus.getOrderByClientOrderId(clientOrderId);
        var orderId = order.getOrderId();
        var currentTriggerPrice = order.getStopPx();
        if(trigger > currentTriggerPrice) {
            logger.debug("Trade - Moving Short Order. NewTriggerPrice: {}, CurrentTriggerPrice: {}", trigger, currentTriggerPrice);
            brokerDao.moveEntryOrder(
                    orderId,
                    triggerPrice
            );
        } else {
            logger.debug("Trade - Bypassing order move.");
        }
    }

    public void processOrderChangedEvent(@Observes OrderChangedEvent orderChangedEvent) {
        OrderEvent orderEvent = orderChangedEvent.getOrderEvent();
        String status = orderEvent.getOrderStatus();

        String orderId = orderEvent.getOrderId();
        String clientOrderId = orderEvent.getClientOrderId();
        if("partial".compareTo(orderEvent.getAction()) == 0) {
            logger.info("Order - Initializing");
        } else {
            logger.info("Order - Changed");
        }

        Order order = null;
        if("New".compareTo(status) == 0) {
            order = new Order(orderEvent);

            if(order.getExecutionInstruction().contains("Close")) {
                orderStatus.addCloseOrder(order);
            } else {
                orderStatus.addEntryOrder(order);
            }

            if(order.getClientOrderId().endsWith(ENTRY.name())) {
                orderStatus.accepted();
            }

        }
        else if("Canceled".compareTo(status) == 0 || "Rejected".compareTo(status) == 0) {

            order = orderStatus.getOrder(orderId);
            orderStatus.removeOrder(orderId);

            if(order.getClientOrderId().endsWith(ENTRY.name())) {
                orderStatus.reset();
            }

        }
        else if("Filled".compareTo(status) == 0) {

            order = orderStatus.getOrder(orderId);
            order.setOrderStatus( orderEvent.getOrderStatus() );
            orderStatus.removeOrder(orderId);

            if(order.getExecutionInstruction().contains("Close")) {
                // IF a Close Order is executed (either System or Manual),
                // THEN all other Orders are Closed
                // AND orderStatus is reset
                brokerDao.cancelAllOrders();
                logger.debug("Order - Other submitted Orders were cancelled");
                orderStatus.reset();
            } else {
                if (!orderStatus.isPositionOpened()) {
                    // IF position is NOT opened
                    // AND Execution Instruction is NOT Close
                    brokerDao.cancelAllOrders();
                    logger.debug("Order - Other submitted Orders were cancelled");
                }

                if (order.getClientOrderId().endsWith(ENTRY.name())) {
                    // IF System Entry Order is filled, then create System Exit Orders
                    String familyOrderId = orderStatus.getFamilyOrderId();
                    brokerDao.createExitOrders(
                            familyOrderId,
                            takeProfitPrice,
                            stopLossPrice,
                            side
                    );
                    logger.debug("Order - Exit orders created");
                    orderStatus.filled();
                }
            }
        }

        String type = order.getOrderType();
        logger.info("Order - {} {}, OrderId: {}, ClientOrderId: {}", type, status, orderId, clientOrderId);
    }

    public void processPositionEvent(@Observes PositionEvent positionEvent) {
        boolean isPositionOpened = positionEvent.isPositionOpened();
        if("partial".compareTo(positionEvent.getAction()) == 0) {
            logger.info("Position - Initializing");
        } else {
            logger.info("Position - Changed");
        }

        if(isPositionOpened) {
            orderStatus.setPosition(positionEvent);
            orderStatus.setPositionOpened(true);

            statistics.setPositionMargin(positionEvent.getPositionMargin());
            statistics.setPositionContracts(positionEvent.getPositionContracts());

        } else {
            orderStatus.setPosition(null);
            orderStatus.setPositionOpened(false);

            statistics.setRealisedPnl(positionEvent.getRealisedPnl());

            // Delete opened ExitOrders if
            if(propertiesFacade.sendClose()) {
                snsDao.publish("Position - Closed", "Closed");
                logger.debug("Position - SNS Message published. For Close.");
            }
        }

        logger.info(
                "Position - Opened: {}, Margin: {}, Contracts: {}, PNL: {}",
                isPositionOpened,
                statistics.getPositionMargin(),
                statistics.getPositionContracts(),
                statistics.getRealisedPnl()
        );
    }
}
