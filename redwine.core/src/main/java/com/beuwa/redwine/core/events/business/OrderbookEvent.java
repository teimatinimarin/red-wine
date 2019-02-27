package com.beuwa.redwine.core.events.business;

import com.beuwa.redwine.core.events.BusinessEvent;

public class OrderbookEvent implements BusinessEvent {
    private String message;

    public OrderbookEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}


