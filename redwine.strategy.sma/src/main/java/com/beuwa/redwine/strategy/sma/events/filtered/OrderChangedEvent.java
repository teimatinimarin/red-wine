package com.beuwa.redwine.strategy.sma.events.filtered;

import com.beuwa.redwine.core.events.business.OrderEvent;
import com.beuwa.redwine.strategy.sma.events.OrderFilteredEvent;

public class OrderChangedEvent implements OrderFilteredEvent {
    private OrderEvent orderEvent;

    public OrderChangedEvent(OrderEvent orderEvent) {
        this.orderEvent = orderEvent;
    }

    public OrderEvent getOrderEvent() {
        return orderEvent;
    }
}
