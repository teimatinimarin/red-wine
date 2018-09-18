package com.beuwa.redwine.strategy.sma.statistics;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class Statistics {
    @Inject
    WindowMap smaMarkPrice;

    public WindowMap getSmaMarkPrice() {
        return smaMarkPrice;
    }
}
