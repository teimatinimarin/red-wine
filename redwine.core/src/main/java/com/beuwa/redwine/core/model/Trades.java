package com.beuwa.redwine.core.model;

import java.util.Map;
import java.util.TreeMap;

public class Trades {
    private String action;
    private Map<Long, Trade> buys = new TreeMap<>();
    private Map<Long, Trade> sells = new TreeMap<>();

    public Trades(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void putEntry(Trade entry) {
        if("Buy".compareTo(entry.getSide()) == 0) {
            buys.put(entry.getEpoch(), entry);
        } else if("Sell".compareTo(entry.getSide()) == 0) {
            sells.put(entry.getEpoch(), entry);
        }
    }

    public Map<Long, Trade> getBuys() {
        return buys;
    }

    public Map<Long, Trade> getSells() {
        return sells;
    }
}

