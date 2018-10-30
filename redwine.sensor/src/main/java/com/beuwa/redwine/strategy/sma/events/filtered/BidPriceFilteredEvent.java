package com.beuwa.redwine.strategy.sma.events.filtered;

public class BidPriceFilteredEvent extends AbstractPriceFilteredEvent {
    public BidPriceFilteredEvent(long epoch, long price) {
        super(epoch, price);
    }
}
