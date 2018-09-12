package com.beuwa.redwine.sensor.observers;

import com.beuwa.redwine.sensor.events.InstrumentEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.apache.logging.log4j.Logger;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InstrumentObserverTest {
    @InjectMocks
    InstrumentObserver instrumentObserver;

    @Mock
    Logger logger;

    @Test
    void observe() {
        InstrumentEvent instrumentEvent = mock(InstrumentEvent.class);
        instrumentObserver.observe(instrumentEvent);
    }
}