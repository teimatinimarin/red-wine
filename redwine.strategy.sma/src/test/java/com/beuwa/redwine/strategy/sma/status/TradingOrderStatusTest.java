package com.beuwa.redwine.strategy.sma.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.apache.logging.log4j.Logger;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TradingOrderStatusTest {
    @InjectMocks
    TradingOrderStatus tradingOrderStatus;

    @Mock
    Logger logger;

    @Test
    void getOrderByClientOrderId() {
        Order manualOrder = new Order();
        manualOrder.setOrderId("1");
        Order systemOrder = new Order();
        systemOrder.setOrderId("2");
        systemOrder.setClientOrderId("aabbccdd-ENTRY");
        tradingOrderStatus.addEntryOrder(manualOrder);
        tradingOrderStatus.addEntryOrder(systemOrder);
        Order returned = tradingOrderStatus.getOrderByClientOrderId("aabbccdd-ENTRY");

        assertEquals("aabbccdd-ENTRY", returned.getClientOrderId());

    }
}