package com.beuwa.redwine.core.model;

import java.util.Map;
import java.util.TreeMap;

public class Orderbook {
    private String action;
    private Map<Long, OrderbookEntry> bids = new TreeMap<>();
    private Map<Long, OrderbookEntry> asks = new TreeMap<>();

    public Orderbook(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void putEntry(OrderbookEntry entry) {
        if("Buy".compareTo(entry.getSide()) == 0) {
            bids.put(entry.getId(), entry);
        } else if("Sell".compareTo(entry.getSide()) == 0) {
            asks.put(entry.getId(), entry);
        }
    }

    public Map<Long, OrderbookEntry> getBids() {
        return bids;
    }

    public Map<Long, OrderbookEntry> getAsks() {
        return asks;
    }
}

