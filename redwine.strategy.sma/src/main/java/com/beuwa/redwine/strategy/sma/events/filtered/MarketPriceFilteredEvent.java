package com.beuwa.redwine.strategy.sma.events.filtered;

public class MarketPriceFilteredEvent extends AbstractPriceFilteredEvent {
    public MarketPriceFilteredEvent(long epoch, long price) {
        super(epoch, price);
    }
}
