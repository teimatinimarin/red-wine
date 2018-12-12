package com.beuwa.redwine.core.events.business;

import com.beuwa.redwine.core.events.BusinessEvent;

public class OrderEvent implements BusinessEvent {
    private String message;
    private String clientOrderId;
    private String clientOrderLinkId;
    private String side;
    private long orderQty;
    private long price;
    private long stopPx;
    private String orderType;
    private String orderStatus;
    private String triggered;

    public OrderEvent(String message,
                      String clientOrderId,
                      String clientOrderLinkId,
                      String side,
                      long orderQty,
                      long price,
                      long stopPx,
                      String orderType,
                      String orderStatus,
                      String triggered) {
        this.message = message;
        this.clientOrderId = clientOrderId;
        this.clientOrderLinkId = clientOrderLinkId;
        this.side = side;
        this.orderQty = orderQty;
        this.price = price;
        this.stopPx = stopPx;
        this.orderType = orderType;
        this.orderStatus = orderStatus;
        this.triggered = triggered;
    }

    public String getMessage() {
        return message;
    }

    public String getClientOrderId() {
        return clientOrderId;
    }

    public String getClientOrderLinkId() {
        return clientOrderLinkId;
    }

    public String getSide() {
        return side;
    }

    public long getOrderQty() {
        return orderQty;
    }

    public long getPrice() {
        return price;
    }

    public long getStopPx() {
        return stopPx;
    }

    public String getOrderType() {
        return orderType;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public String getTriggered() {
        return triggered;
    }

    public static class OrderEventBuilder {
        private String message;
        private String clientOrderId;
        private String clientOrderLinkId;
        private String side;
        private long orderQty;
        private long price;
        private long stopPx;
        private String orderType;
        private String orderStatus;
        private String triggered;

        public OrderEventBuilder message(String message) {
            this.message = message;
            return this;
        }

        public OrderEventBuilder clientOrderId(String clientOrderId) {
            this.clientOrderId = clientOrderId;
            return this;
        }

        public OrderEventBuilder clientOrderLinkId(String clientOrderLinkId) {
            this.clientOrderLinkId = clientOrderLinkId;
            return this;
        }

        public OrderEventBuilder side(String side) {
            this.side = side;
            return this;
        }

        public OrderEventBuilder orderQty(long orderQty) {
            this.orderQty = orderQty;
            return this;
        }

        public OrderEventBuilder price(long price) {
            this.price = price;
            return this;
        }

        public OrderEventBuilder stopPx(long stopPx) {
            this.stopPx = stopPx;
            return this;
        }

        public OrderEventBuilder orderType(String orderType) {
            this.orderType = orderType;
            return this;
        }

        public OrderEventBuilder orderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
            return this;
        }

        public OrderEventBuilder triggered(String triggered) {
            this.triggered = triggered;
            return this;
        }

        public OrderEvent build() {
            return new OrderEvent(
                    message,
                    clientOrderId,
                    clientOrderLinkId,
                    side,
                    orderQty,
                    price,
                    stopPx,
                    orderType,
                    orderStatus,
                    triggered
            );
        }
    }
}

