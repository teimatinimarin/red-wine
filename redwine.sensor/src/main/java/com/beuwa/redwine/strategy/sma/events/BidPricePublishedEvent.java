package com.beuwa.redwine.strategy.sma.events;

public class BidPricePublishedEvent extends AbstractPricePublishedEvent {
    public BidPricePublishedEvent(long epoch, long price) {
        super(epoch, price);
    }
}
