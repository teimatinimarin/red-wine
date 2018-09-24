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
        windowMap.put(epoch + 1, 300L);
        windowMap.put(epoch + 2, 500L);

        assertEquals(300L, windowMap.getSmaCurrent());
        assertEquals(100L, windowMap.getSmaMin());
        assertEquals(300L, windowMap.getSmaMax());
        assertEquals(500L, windowMap.getPriceCurrent());
        assertEquals(3, windowMap.size());
    }
}