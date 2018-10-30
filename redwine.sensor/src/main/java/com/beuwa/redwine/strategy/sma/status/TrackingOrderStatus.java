package com.beuwa.redwine.strategy.sma.status;

public class TrackingOrderStatus extends OrderStatus {
    @Override
    public void reset() {
       super.reset();
    }

    @Override
    public void opened() {
        super.opened();
    }

    @Override
    public void accepted() {
        super.accepted();
    }
}
