package com.beuwa.redwine.strategy.sma.utils;

public enum Fibonacci {
    FIBONACCI_382(382),
    FIBONACCI_090(90),
    FIBONACCI_008(8);

    private int perMille;

    Fibonacci(int perMille) {
        this.perMille = perMille;
    }

    public int getPerMille() {
        return perMille;
    }
}
