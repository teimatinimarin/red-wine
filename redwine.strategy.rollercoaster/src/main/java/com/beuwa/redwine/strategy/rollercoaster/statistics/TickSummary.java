package com.beuwa.redwine.strategy.rollercoaster.statistics;

import com.beuwa.redwine.core.events.business.TradeEvent;

import java.util.ArrayList;
import java.util.List;

public class TickSummary {
    private long count = 0;
    private long epoch;
    private double firstPrice;
    private double lastPrice;
    private long volume;
    private List<TradeEvent> events = new ArrayList<>();

    public void put(long epoch, TradeEvent trade) {
        events.add(trade);
        this.count++;
        this.epoch = epoch;
        if(count == 1) {
            this.firstPrice = trade.getPrice();
        }
        this.lastPrice = trade.getPrice();
        this.volume += trade.getSize();
    }

    public long getCount() {
        return count;
    }

    public long getEpoch() {
        return epoch;
    }

    public double getFirstPrice() {
        return firstPrice;
    }

    public double getLastPrice() {
        return lastPrice;
    }

    public long getVolume() {
        return volume;
    }

    public List<TradeEvent> getEvents() {
        return events;
    }
}
