package com.beuwa.redwine.strategy.sma.status;

import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public abstract class OrderStatus {
    @Inject
    private Logger logger;

    // No System Order has been Sent
    protected static final int EMPTY = 0;
    // System Order was Sent, not created yet
    protected static final int SENT = 1;
    // System Order was Created
    protected static final int ACCEPTED = 2;
    // Position was opened
    protected static final int FILLED = 3;

    protected boolean positionOpened = false;

    protected int status = EMPTY;

    protected void reset() {
        status = EMPTY;
        logger.debug("OrderStatus - Status set to EMPTY");
    }

    public void sent() {
        status = SENT;
        logger.debug("OrderStatus - Status set to SENT");
    }

    public void accepted() {
        status = ACCEPTED;
        logger.debug("OrderStatus - Status set to ACCEPTED");
    }

    public void filled() {
        status = FILLED;
        logger.debug("OrderStatus - Status set to FILLED");
    }

    public int getStatus() {
        return status;
    }

    public boolean isEmpty() {
        return EMPTY == status;
    }

    public boolean isSent() {
        return SENT == status;
    }

    public boolean isAccepted() {
        return ACCEPTED == status;
    }

    public boolean isFilled() {
        return FILLED == status;
    }

    public void setPositionOpened(boolean positionOpened) {
        this.positionOpened = positionOpened;
    }

    public boolean isPositionOpened() {
        return positionOpened;
    }
}
