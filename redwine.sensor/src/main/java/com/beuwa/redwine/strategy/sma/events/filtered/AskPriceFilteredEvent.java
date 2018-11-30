package com.beuwa.redwine.strategy.sma.events.filtered;

public class AskPriceFilteredEvent extends AbstractPriceFilteredEvent {
    public AskPriceFilteredEvent(long epoch, long price) {
        super(epoch, price);
    }
}
