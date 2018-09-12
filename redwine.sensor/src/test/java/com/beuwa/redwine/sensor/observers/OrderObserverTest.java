package com.beuwa.redwine.sensor.observers;

import com.beuwa.redwine.sensor.events.OrderEvent;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class OrderObserverTest {
    @InjectMocks
    OrderObserver orderObserver;

    @Mock
    Logger logger;

    @Test
    void observe() {
        OrderEvent orderEvent = mock(OrderEvent.class);
        orderObserver.observe(orderEvent);
    }
}