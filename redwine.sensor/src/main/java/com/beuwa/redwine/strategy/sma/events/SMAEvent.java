package com.beuwa.redwine.strategy.sma.events;

import com.beuwa.redwine.strategy.sma.statistics.WindowMap;

public class SMAEvent implements StatisticsEvent {
    WindowMap windowMap;

    public SMAEvent(WindowMap windowMap) {
        this.windowMap = windowMap;
    }

    public WindowMap getWindowMap() {
        return windowMap;
    }
}
