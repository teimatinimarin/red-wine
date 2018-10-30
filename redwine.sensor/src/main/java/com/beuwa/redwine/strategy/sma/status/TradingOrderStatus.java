package com.beuwa.redwine.strategy.sma.status;

import com.beuwa.redwine.core.events.business.OrderEvent;
import com.beuwa.redwine.strategy.sma.constants.Orders;

public class TradingOrderStatus extends OrderStatus {
    private OrderEvent parent;
    private OrderEvent takeProfit;
    private OrderEvent stopLoss;

    public void setOrder(OrderEvent orderEvent) {
        String orderStatus = orderEvent.getOrderStatus();
        if("New".compareTo(orderStatus) == 0) {
            String clientOrderId = orderEvent.getClientOrderId();
            if (clientOrderId.endsWith(Orders.PARENT.toString())) {
                parent = orderEvent;
            } else if (clientOrderId.endsWith(Orders.TAKE_PROFIT.toString())) {
                takeProfit = orderEvent;
            } else if (clientOrderId.endsWith(Orders.STOP_LOSS.toString())) {
                stopLoss = orderEvent;
            }

            if(takeProfit != null && stopLoss != null) {
                // This covers the case where redwine is started and PARENT order was Filled and TP/SL are New
                accepted();
            }
        }
        else if("Filled".compareTo(orderStatus) == 0) {
            String clientOrderId = orderEvent.getClientOrderId();
            if (clientOrderId.endsWith(Orders.PARENT.toString())) {
                opened();
            } else if (clientOrderId.endsWith(Orders.TAKE_PROFIT.toString())) {
                reset();
            } else if (clientOrderId.endsWith(Orders.STOP_LOSS.toString())) {
                reset();
            }
        }
        else if("Canceled".compareTo(orderStatus) == 0) {
            // This handles manual cancelation of orders
            String clientOrderId = orderEvent.getClientOrderId();
            if (clientOrderId.endsWith(Orders.TAKE_PROFIT.toString())) {
                takeProfit = null;
            } else if (clientOrderId.endsWith(Orders.STOP_LOSS.toString())) {
                stopLoss = null;
            }
            if(takeProfit == null && stopLoss == null) {
                // TAKE_PROFIT and STOP_LOSS were manually cancelled
                reset();
            }
        }
    }

    @Override
    protected void reset() {
        status = EMPTY;
        parent = null;
        takeProfit = null;
        stopLoss = null;
    }

    @Override
    public void sent() {
        super.sent();
    }

    public OrderEvent getParent() {
        return parent;
    }

    public OrderEvent getTakeProfit() {
        return takeProfit;
    }

    public OrderEvent getStopLoss() {
        return stopLoss;
    }
}
