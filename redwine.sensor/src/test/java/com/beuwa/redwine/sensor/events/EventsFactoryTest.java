package com.beuwa.redwine.sensor.events;

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
        URL uri = getClass().getClassLoader().getResource("com/beuwa/redwine/sensor/events/instrument-event.json");
        Path path = Paths.get(uri.toURI());
        byte[] content = Files.readAllBytes(path);
        String message = new String(content);

        BusinessEvent event = eventsFactory.build(message);
        assertTrue(event instanceof InstrumentEvent);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "liquidation-event.json",delimiter = '^')
    void createLiquidationEvent(String message) {
        BusinessEvent event = eventsFactory.build(message);
        assertTrue(event instanceof LiquidationEvent);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "order-event.json",delimiter = '^')
    void createOrderEvent(String message) {
        BusinessEvent event = eventsFactory.build(message);
        assertTrue(event instanceof OrderEvent);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "position-event.json",delimiter = '^')
    void createPositionEvent(String message) {
        BusinessEvent event = eventsFactory.build(message);
        assertTrue(event instanceof PositionEvent);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "quote-event.json",delimiter = '^')
    void createQuoteEvent(String message) {
        BusinessEvent event = eventsFactory.build(message);
        assertTrue(event instanceof QuoteEvent);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "trade-event.json",delimiter = '^')
    void createTradeEvent(String message) {
        BusinessEvent event = eventsFactory.build(message);
        assertTrue(event instanceof TradeEvent);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "wallet-event.json",delimiter = '^')
    void createWalletEvent(String message) {
        BusinessEvent event = eventsFactory.build(message);
        assertTrue(event instanceof WalletEvent);

        WalletEvent walletEvent = (WalletEvent)event;
        assertTrue(walletEvent.getAmount() > 0);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "invalid-event.json",delimiter = '^')
    void createInvalidEvent(String message) {
        BusinessEvent event = eventsFactory.build(message);
        assertNull(event);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "no-table-property-event.json",delimiter = '^')
    void createNoTablePropertyEvent(String message) {
        BusinessEvent event = eventsFactory.build(message);
        assertNull(event);
    }
}