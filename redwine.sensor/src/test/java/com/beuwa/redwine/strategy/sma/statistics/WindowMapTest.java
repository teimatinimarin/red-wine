package com.beuwa.redwine.strategy.sma.statistics;

import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class WindowMapTest {
    @InjectMocks
    WindowMap windowMap;

    @Mock
    private Logger logger;

    @Test
    void put() {
        long epoch = Instant.now().toEpochMilli();
        windowMap.put(epoch - 300001, 100L);
        windowMap.put(epoch + 0, 100L);
        windowMap.put(epoch + 1, 200L);
        windowMap.put(epoch + 2, 300L);

        assertEquals(200L, windowMap.getSma());
        assertEquals(100L, windowMap.getMin());
        assertEquals(300L, windowMap.getMax());
        assertEquals(300L, windowMap.getCurrent());
        assertEquals(3, windowMap.size());
    }
}