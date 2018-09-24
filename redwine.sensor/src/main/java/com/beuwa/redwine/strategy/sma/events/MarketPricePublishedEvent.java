package com.beuwa.redwine.strategy.sma.events;

public class MarketPricePublishedEvent extends AbstractPricePublishedEvent {
    public MarketPricePublishedEvent(long epoch, long price) {
        super(epoch, price);
    }
}
