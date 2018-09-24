package com.beuwa.redwine.strategy.sma.observers;

import com.beuwa.redwine.core.events.InstrumentEvent;
import com.beuwa.redwine.strategy.sma.events.AskPricePublishedEvent;
import com.beuwa.redwine.strategy.sma.events.BidPricePublishedEvent;
import com.beuwa.redwine.strategy.sma.events.MarketPricePublishedEvent;
import com.beuwa.redwine.strategy.sma.events.PricePublishedEvent;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.enterprise.event.Event;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InstrumentObserverTest {
    @InjectMocks
    InstrumentObserver instrumentObserver;

    @Mock
    private Logger logger;

    @Mock
    private Event<PricePublishedEvent> event;

    @Test
    void processIntrumentMarkEvent() {
        InstrumentEvent instrumentEvent = new InstrumentEvent
                .InstrumentEventBuilder()
                .epoch(1000000L)
                .markPrice(6610000)
                .build();
        instrumentObserver.processIntrumentEvent(instrumentEvent);

        verify(event, times(1)).fire(any(MarketPricePublishedEvent.class));
    }

    @Test
    void processIntrumentBidEvent() {
        InstrumentEvent instrumentEvent = new InstrumentEvent
                .InstrumentEventBuilder()
                .epoch(1000000L)
                .bidPrice(6610000)
                .build();
        instrumentObserver.processIntrumentEvent(instrumentEvent);

        verify(event, times(1)).fire(any(BidPricePublishedEvent.class));
    }

    @Test
    void processIntrumentAskEvent() {
        InstrumentEvent instrumentEvent = new InstrumentEvent
                .InstrumentEventBuilder()
                .epoch(1000000L)
                .askPrice(6610000)
                .build();
        instrumentObserver.processIntrumentEvent(instrumentEvent);

        verify(event, times(1)).fire(any(AskPricePublishedEvent.class));
    }

    @Test
    void processIntrumentNoFire() {
        InstrumentEvent instrumentEvent = new InstrumentEvent
                .InstrumentEventBuilder()
                .epoch(1000000L)
                .build();
        instrumentObserver.processIntrumentEvent(instrumentEvent);

        verify(event, never()).fire(any());
    }

    @Test
    void processIntrumentMultipleFire() {
        InstrumentEvent instrumentEvent = new InstrumentEvent
                .InstrumentEventBuilder()
                .epoch(1000000L)
                .markPrice(6610000)
                .bidPrice(6610000)
                .askPrice(6610000)
                .build();
        instrumentObserver.processIntrumentEvent(instrumentEvent);

        verify(event, times(3)).fire(any());
    }
}