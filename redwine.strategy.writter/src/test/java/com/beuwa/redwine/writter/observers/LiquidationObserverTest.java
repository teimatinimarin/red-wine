package com.beuwa.redwine.writter.observers;

import com.beuwa.redwine.core.events.business.LiquidationEvent;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class LiquidationObserverTest {
    @InjectMocks
    LiquidationObserver liquidationObserver;

    @Mock
    Logger logger;

    @Test
    void observe() {
        LiquidationEvent instrumentEvent = mock(LiquidationEvent.class);
        liquidationObserver.observe(instrumentEvent);
    }
}