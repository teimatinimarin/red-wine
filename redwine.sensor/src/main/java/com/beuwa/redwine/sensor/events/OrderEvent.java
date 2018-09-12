package com.beuwa.redwine.sensor.events;

public class OrderEvent implements BusinessEvent {
    private String message;

    private OrderEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static class OrderEventBuilder {
        private String message;

        public OrderEventBuilder message(String message) {
            this.message = message;
            return this;
        }

        public OrderEvent build() {
            return new OrderEvent(message);
        }
    }
}

