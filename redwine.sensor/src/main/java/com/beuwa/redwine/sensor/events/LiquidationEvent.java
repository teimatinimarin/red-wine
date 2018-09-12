package com.beuwa.redwine.sensor.events;

public class LiquidationEvent implements BusinessEvent {
    private String message;

    private LiquidationEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static class LiquidationEventBuilder {
        private String message;

        public LiquidationEventBuilder message(String message) {
            this.message = message;
            return this;
        }

        public LiquidationEvent build() {
            return new LiquidationEvent(message);
        }
    }
}
