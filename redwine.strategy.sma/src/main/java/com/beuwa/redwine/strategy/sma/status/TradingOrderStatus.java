package com.beuwa.redwine.strategy.sma.status;

import com.beuwa.redwine.core.events.business.PositionEvent;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TradingOrderStatus extends OrderStatus {
    @Inject
    private Logger logger;

    private String familyOrderId;
    private Map<String, Order> entryOrders = new HashMap<>();
    private Map<String, Order> closeOrders = new HashMap<>();

    private PositionEvent positionEvent;

    public void reset() {
        super.reset();
        this.familyOrderId = null;
    }

    public void setFamilyOrderId(String familyOrderId) {
        this.familyOrderId = familyOrderId;
    }

    public String getFamilyOrderId() {
        return familyOrderId;
    }

    public void setPosition(PositionEvent positionEvent) {
        this.positionEvent = positionEvent;
    }

    public void removeOrder(String orderId) {
        if(null != getEntryOrder(orderId)) {
            removeEntryOrder(orderId);
        }
        if(null != getCloseOrder(orderId)) {
            removeCloseOrder(orderId);
        }
    }

    public Order getOrder(String orderId) {
        Order order = getEntryOrder(orderId);
        if(null == order) {
            order = getCloseOrder(orderId);
        }

        return order;
    }

    public Order getOrderByClientOrderId(String clientOrderId) {
        Map<String, Order> allOrders = new HashMap<>();
        allOrders.putAll(entryOrders);
        allOrders.putAll(closeOrders);

        Optional<Map.Entry<String, Order>> optionalEntry = allOrders.entrySet().stream()
                .filter(map -> null != map.getValue().getClientOrderId() && map.getValue().getClientOrderId().equals(clientOrderId))
                .findFirst();
        return optionalEntry.get().getValue();
    }

    public void addEntryOrder(Order order) {
        entryOrders.put(order.getOrderId(), order);
        logger.info("Order - There are {} Entry Orders", entryOrders.size());
    }

    private Order getEntryOrder(String orderId) {
        return entryOrders.get(orderId);
    }

    private void removeEntryOrder(String orderId) {
        entryOrders.remove(orderId);
        logger.info("Order - There are {} Entry Orders", entryOrders.size());
    }

    public void addCloseOrder(Order order) {
        closeOrders.put(order.getOrderId(), order);
        logger.info("Order - There are {} Close Orders", closeOrders.size());
    }

    private Order getCloseOrder(String orderId) {
        return closeOrders.get(orderId);
    }

    private void removeCloseOrder(String orderId) {
        closeOrders.remove(orderId);
        logger.info("Order - There are {} Close Orders", closeOrders.size());
    }

    @Override
    public void sent() {
        super.sent();
    }

}
