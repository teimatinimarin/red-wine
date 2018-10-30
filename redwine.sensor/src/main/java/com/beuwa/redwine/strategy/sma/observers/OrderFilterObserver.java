package com.beuwa.redwine.strategy.sma.observers;

import com.beuwa.redwine.core.events.business.OrderEvent;
import com.beuwa.redwine.strategy.sma.events.filtered.OrderChangedEvent;
import com.beuwa.redwine.strategy.sma.events.OrderFilteredEvent;
import org.apache.logging.log4j.Logger;

import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

public class OrderFilterObserver {
    @Inject
    Logger logger;

    @Inject
    private Event<OrderFilteredEvent> event;

    public void processOrderEvent(@Observes OrderEvent orderEvent) {
        if(orderEvent.getOrderStatus() != null) {
            logger.info("Order: {} -> {}", orderEvent.getClientOrderId(), orderEvent.getOrderStatus());
            event.fire(new OrderChangedEvent(orderEvent));
        }
    }
}
