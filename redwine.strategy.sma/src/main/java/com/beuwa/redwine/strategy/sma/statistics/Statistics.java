package com.beuwa.redwine.strategy.sma.statistics;

import com.beuwa.redwine.strategy.sma.constants.Fibonacci;
import com.beuwa.redwine.strategy.sma.constants.Status;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class Statistics {
    @Inject
    private WindowMap smaMarkPrice;

    private long sma = 0L;
    private long max = 0L;
    private long min = 0L;

    private long bid = 0L;
    private long bidSize = 0L;
    private long ask = 0L;
    private long askSize = 0L;

    private long range = 0L;
    private long targetRange = 0L;

    private long walletBalance;
    private long positionMargin;
    private long positionContracts;
    private long realisedPnl;

    public int addMarkPrice(long epoch, long markPrice) {
        int status = -1;

        boolean calculated = smaMarkPrice.put(epoch, markPrice);
        sma = smaMarkPrice.getSmaCurrent();
        if(calculated) {
            if(smaMarkPrice.getSmaMax() > max) {
                status = Status.NEW_MAX;
            } else if(smaMarkPrice.getSmaMin() < min) {
                status = Status.NEW_MIN;
            } else {
                status = Status.EXISTING;
            }

            max = smaMarkPrice.getSmaMax();
            min = smaMarkPrice.getSmaMin();

        } else {
            max = smaMarkPrice.getSmaMax();
            min = smaMarkPrice.getSmaMin();
            status = Status.WARMING_UP;
        }

        return status;
    }

    public void calculateRages() {
        range = max - min;
        targetRange = Math.round(sma * Fibonacci.FIBONACCI_008 / 1000f);
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

    public long getBidSize() {
        return bidSize;
    }

    public void setBidSize(long bidSize) {
        this.bidSize = bidSize;
    }

    public long getAsk() {
        return ask;
    }

    public void setAsk(long ask) {
        this.ask = ask;
    }

    public long getAskSize() {
        return askSize;
    }

    public void setAskSize(long askSize) {
        this.askSize = askSize;
    }

    public long size() {
        return smaMarkPrice.size();
    }

    public long getRange() {
        return range;
    }

    public long getTargetRange() {
        return targetRange;
    }

    public void setWalletBalance(long walletBalance) {
        this.walletBalance = walletBalance;
    }

    public long getWalletBalance() {
        return this.walletBalance;
    }

    public long getPositionMargin() {
        return positionMargin;
    }

    public void setPositionMargin(long positionMargin) {
        this.positionMargin = positionMargin;
    }

    public long getPositionContracts() {
        return positionContracts;
    }

    public void setPositionContracts(long positionContracts) {
        this.positionContracts = positionContracts;
    }

    public long getRealisedPnl() {
        return realisedPnl;
    }

    public void setRealisedPnl(long realisedPnl) {
        this.realisedPnl = realisedPnl;
    }
}
