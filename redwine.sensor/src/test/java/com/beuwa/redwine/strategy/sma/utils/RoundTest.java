package com.beuwa.redwine.strategy.sma.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoundTest {

    @Test
    void toNear50Cents() {
        Round round = new Round();

        assertEquals(1234.00, round.toNear50Cents(123412));
        assertEquals(1234.50, round.toNear50Cents(123490));
        assertEquals(1234.00, round.toNear50Cents(123425));
        assertEquals(1234.50, round.toNear50Cents(123450));
        assertEquals(1235.00, round.toNear50Cents(123500));
    }
}