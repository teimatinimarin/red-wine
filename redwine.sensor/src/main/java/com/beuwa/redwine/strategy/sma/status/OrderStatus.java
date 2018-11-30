package com.beuwa.redwine.strategy.sma.status;

public abstract class OrderStatus {
    protected static final int EMPTY = 0;
    protected static final int SENT = 1;
    protected static final int ACCEPTED = 2;
    protected static final int OPENED = 3;

    protected int status = EMPTY;

    protected void reset() {
        status = EMPTY;
    }

    protected void sent() {
        status = SENT;
    }

    protected void accepted() {
        status = ACCEPTED;
    }

    protected void opened() {
        status = OPENED;
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

    public boolean isOpened() {
        return OPENED == status;
    }
}
