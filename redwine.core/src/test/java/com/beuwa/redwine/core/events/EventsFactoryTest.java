package com.beuwa.redwine.core.events;

import com.beuwa.redwine.core.events.BusinessEvent;
import com.beuwa.redwine.core.events.EventsFactory;
import com.beuwa.redwine.core.events.business.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EventsFactoryTest {
    @InjectMocks
    EventsFactory eventsFactory;

    @Test
    void createInstrumentEvent() throws URISyntaxException, IOException {
        URL uri = getClass().getClassLoader().getResource("com/beuwa/redwine/core/events/instrument-event.json");
        Path path = Paths.get(uri.toURI());
        byte[] content = Files.readAllBytes(path);
        String message = new String(content);

        BusinessEvent event = eventsFactory.build(message)[0];
        assertTrue(event instanceof InstrumentEvent);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "liquidation-event.json",delimiter = '^')
    void createLiquidationEvent(String message) {
        BusinessEvent event = eventsFactory.build(message)[0];
        assertTrue(event instanceof LiquidationEvent);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "order-event.json",delimiter = '^')
    void createOrderEventNew(String message) {
        BusinessEvent[] events = eventsFactory.build(message);
        assertTrue(events[0] instanceof OrderEvent);
        assertTrue(events[1] instanceof OrderEvent);
        assertTrue(events[2] instanceof OrderEvent);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "order-event-parent-cancelled.json",delimiter = '^')
    void createOrderEventParentCancelled(String message) {
        BusinessEvent event = eventsFactory.build(message)[0];
        assertTrue(event instanceof OrderEvent);
        assertEquals("Canceled", ((OrderEvent)event).getOrderStatus());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "order-event-children-cancelled.json",delimiter = '^')
    void createOrderEventChildrenCancelled(String message) {
        BusinessEvent[] events = eventsFactory.build(message);
        assertTrue(events[0] instanceof OrderEvent);
        assertEquals("Canceled", ((OrderEvent)events[0]).getOrderStatus());
        assertTrue(events[1] instanceof OrderEvent);
        assertEquals("Canceled", ((OrderEvent)events[1]).getOrderStatus());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "position-event.json",delimiter = '^')
    void createPositionEvent(String message) {
        BusinessEvent event = eventsFactory.build(message)[0];
        assertTrue(event instanceof PositionEvent);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "quote-event.json",delimiter = '^')
    void createQuoteEvent(String message) {
        BusinessEvent event = eventsFactory.build(message)[0];
        assertTrue(event instanceof QuoteEvent);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "trade-event.json",delimiter = '^')
    void createTradeEvent(String message) {
        BusinessEvent event = eventsFactory.build(message)[0];
        assertTrue(event instanceof TradeEvent);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "wallet-event.json",delimiter = '^')
    void createWalletEvent(String message) {
        BusinessEvent event = eventsFactory.build(message)[0];

        WalletEvent walletEvent = (WalletEvent)event;
        assertTrue(walletEvent.getAmount() > 0);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "invalid-event.json",delimiter = '^')
    void createInvalidEvent(String message) {
        BusinessEvent[] events = eventsFactory.build(message);
        assertNull(events);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "no-table-property-event.json",delimiter = '^')
    void createNoTablePropertyEvent(String message) {
        BusinessEvent[] events = eventsFactory.build(message);
        assertNull(events);
    }
}
