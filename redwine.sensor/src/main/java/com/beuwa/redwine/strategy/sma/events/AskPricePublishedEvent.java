package com.beuwa.redwine.strategy.sma.events;

public class AskPricePublishedEvent extends AbstractPricePublishedEvent {
    public AskPricePublishedEvent(long epoch, long price) {
        super(epoch, price);
    }
}
