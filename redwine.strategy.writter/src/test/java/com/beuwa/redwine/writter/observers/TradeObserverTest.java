package com.beuwa.redwine.writter.observers;

import com.beuwa.redwine.core.events.business.TradeEvent;
import com.beuwa.redwine.writter.utils.WritterUtils;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class TradeObserverTest {
    @InjectMocks
    TradeObserver quoteObserver;

    @Mock
    WritterUtils writterUtils;

    @Mock
    Logger logger;

    @Test
    void observe() throws SQLException {
        TradeEvent quoteEvent = mock(TradeEvent.class);
        quoteObserver.observe(quoteEvent);
    }
}