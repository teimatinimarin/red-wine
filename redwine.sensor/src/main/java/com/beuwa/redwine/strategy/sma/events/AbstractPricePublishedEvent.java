package com.beuwa.redwine.strategy.sma.events;

class AbstractPricePublishedEvent implements PricePublishedEvent {
    protected long epoch;
    protected long price;

    protected AbstractPricePublishedEvent(long epoch, long price) {
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
