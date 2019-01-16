package com.beuwa.redwine.strategy.sma.status;

import com.beuwa.redwine.core.events.business.OrderEvent;

public class Order {
    private String orderId;
    private String clientOrderId;
    private String side;
    private long orderQty;
    private long price;
    private long stopPx;
    private String orderType;
    private String orderStatus;
    private String executionInstruction;
    private String triggered;

    public Order() {
        super();
    }

    public Order(OrderEvent orderEvent) {
        this.orderId = orderEvent.getOrderId();
        this.clientOrderId = orderEvent.getClientOrderId();
        this.side = orderEvent.getSide();
        this.orderQty = orderEvent.getOrderQty();
        this.price = orderEvent.getPrice();
        this.stopPx = orderEvent.getStopPx();
        this.orderType = orderEvent.getOrderType();
        this.orderStatus = orderEvent.getOrderStatus();
        this.executionInstruction = orderEvent.getExecutionInstruction();
        this.triggered = orderEvent.getTriggered();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getClientOrderId() {
        return clientOrderId;
    }

    public void setClientOrderId(String clientOrderId) {
        this.clientOrderId = clientOrderId;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public long getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(long orderQty) {
        this.orderQty = orderQty;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public long getStopPx() {
        return stopPx;
    }

    public void setStopPx(long stopPx) {
        this.stopPx = stopPx;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getExecutionInstruction() {
        return executionInstruction;
    }

    public void setExecutionInstruction(String executionInstruction) {
        this.executionInstruction = executionInstruction;
    }

    public String getTriggered() {
        return triggered;
    }

    public void setTriggered(String triggered) {
        this.triggered = triggered;
    }
}
