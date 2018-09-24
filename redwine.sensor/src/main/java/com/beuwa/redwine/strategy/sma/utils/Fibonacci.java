package com.beuwa.redwine.strategy.sma.utils;

public enum Fibonacci {
    _382(382L),
    _090(90L),
    _008(8L);

    private long perMille;

    Fibonacci(long perMille) {
        this.perMille = perMille;
    }

    public long getPerMille() {
        return perMille;
    }
}
