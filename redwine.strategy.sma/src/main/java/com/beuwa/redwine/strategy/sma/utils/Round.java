package com.beuwa.redwine.strategy.sma.utils;

public class Round {
    public double toNear50Cents(long number) {
        var roundTo = 50;
        double rounded = Math.round(number / roundTo);
        return (roundTo * rounded)/100;
    }
}
