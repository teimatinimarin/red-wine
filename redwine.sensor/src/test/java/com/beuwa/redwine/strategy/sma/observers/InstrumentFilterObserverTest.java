package com.beuwa.redwine.strategy.sma.observers;

import com.beuwa.redwine.core.events.business.InstrumentEvent;
import com.beuwa.redwine.strategy.sma.events.filtered.AskPriceFilteredEvent;
import com.beuwa.redwine.strategy.sma.events.filtered.BidPriceFilteredEvent;
import com.beuwa.redwine.strategy.sma.events.filtered.MarketPriceFilteredEvent;
import com.beuwa.redwine.strategy.sma.events.PriceFilteredEvent;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.enterprise.event.Event;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InstrumentFilterObserverTest {
    @InjectMocks
    InstrumentFilterObserver instrumentFilterObserver;

    @Mock
    private Logger logger;

    @Mock
    private Event<PriceFilteredEvent> event;

    @Test
    void processIntrumentMarkEvent() {
        InstrumentEvent instrumentEvent = new InstrumentEvent
                .InstrumentEventBuilder()
                .epoch(1000000L)
                .markPrice(6610000)
                .build();
        instrumentFilterObserver.processIntrumentEvent(instrumentEvent);

        verify(event, times(1)).fire(any(MarketPriceFilteredEvent.class));
    }

    @Test
    @Disabled
    void processIntrumentBidEvent() {
        InstrumentEvent instrumentEvent = new InstrumentEvent
                .InstrumentEventBuilder()
                .epoch(1000000L)
                .bidPrice(6610000)
                .build();
        instrumentFilterObserver.processIntrumentEvent(instrumentEvent);

        verify(event, times(1)).fire(any(BidPriceFilteredEvent.class));
    }

    @Test
    @Disabled
    void processIntrumentAskEvent() {
        InstrumentEvent instrumentEvent = new InstrumentEvent
                .InstrumentEventBuilder()
                .epoch(1000000L)
                .askPrice(6610000)
                .build();
        instrumentFilterObserver.processIntrumentEvent(instrumentEvent);

        verify(event, times(1)).fire(any(AskPriceFilteredEvent.class));
    }

    @Test
    void processIntrumentNoFire() {
        InstrumentEvent instrumentEvent = new InstrumentEvent
                .InstrumentEventBuilder()
                .epoch(1000000L)
                .build();
        instrumentFilterObserver.processIntrumentEvent(instrumentEvent);

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
        instrumentFilterObserver.processIntrumentEvent(instrumentEvent);

        verify(event, times(1)).fire(any());
    }
}