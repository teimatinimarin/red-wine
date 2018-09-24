package com.beuwa.redwine.strategy.sma.statistics;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class Statistics {
    public enum MIN_MAX_STATUS{NEW_MIN, NEW_MAX, EXISTING, WARMING_UP}

    @Inject
    private WindowMap smaMarkPrice;

    private long sma = 0L;
    private long max = 0L;
    private long min = 0L;

    private long bid = 0L;
    private long ask = 0L;

    public MIN_MAX_STATUS addMarkPrice(long epoch, long markPrice) {
        MIN_MAX_STATUS status = null;

        boolean calculated = smaMarkPrice.put(epoch, markPrice);
        sma = smaMarkPrice.getSmaCurrent();
        if(calculated) {
            if(smaMarkPrice.getSmaMax() > max) {
                status = MIN_MAX_STATUS.NEW_MAX;
            } else if(smaMarkPrice.getSmaMin() < min) {
                status = MIN_MAX_STATUS.NEW_MIN;
            } else {
                status = MIN_MAX_STATUS.EXISTING;
            }

            max = smaMarkPrice.getSmaMax();
            min = smaMarkPrice.getSmaMin();

        } else {
            max = smaMarkPrice.getSmaMax();
            min = smaMarkPrice.getSmaMin();
            status = MIN_MAX_STATUS.WARMING_UP;
        }

        return status;
    }

    public String getLastTickTimestamp() {
        return smaMarkPrice.getLastTickTimestamp();
    }

    public long getSma() {
        return sma;
    }

    public long getPriceCurrent() {
        return smaMarkPrice.getPriceCurrent();
    }

    public long getMax() {
        return max;
    }

    public long getMin() {
        return min;
    }

    public long getBid() {
        return bid;
    }

    public void setBid(long bid) {
        this.bid = bid;
    }

    public long getAsk() {
        return ask;
    }

    public void setAsk(long ask) {
        this.ask = ask;
    }

    public long size() {
        return smaMarkPrice.size();
    }
}
