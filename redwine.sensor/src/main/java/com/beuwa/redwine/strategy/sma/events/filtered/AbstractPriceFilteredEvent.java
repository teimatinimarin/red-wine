package com.beuwa.redwine.strategy.sma.events.filtered;

import com.beuwa.redwine.strategy.sma.events.PriceFilteredEvent;

class AbstractPriceFilteredEvent implements PriceFilteredEvent {
    protected long epoch;
    protected long price;

    protected AbstractPriceFilteredEvent(long epoch, long price) {
        this.epoch = epoch;
        this.price = price;
    }

    public long getEpoch() {
        return epoch;
    }

    public long getPrice() {
        return this.price;
    }
}
