package com.beuwa.redwine.strategy.sma.utils;

import com.beuwa.redwine.core.config.PropertiesFacade;
import com.beuwa.redwine.strategy.sma.statistics.Statistics;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SimpleOrderQuantityCalculatorTest {
    @InjectMocks
    SimpleOrderQuantityCalculator simpleOrderQuantityCalculator;

    @Mock
    Logger logger;

    @Mock
    private Statistics statistics;

    @Mock
    private PropertiesFacade propertiesFacade;

    @Test
    void satoshisToInvest() {
        when(statistics.getWalletBalance()).thenReturn(1000010L);
        when(statistics.getRealisedPnl()).thenReturn(10L);
        when(statistics.getPositionMargin()).thenReturn(10L);
        when(propertiesFacade.getMaxInvest()).thenReturn(5000000L);
        when(propertiesFacade.getPercentageToInvest()).thenReturn(90L);

        assertEquals(900009L, simpleOrderQuantityCalculator.satoshisToInvest());
    }

    @Test
    void satoshisToInvestMaxToInvest() {
        var maxToInvest = 500L;
        when(statistics.getWalletBalance()).thenReturn(1000010L);
        when(statistics.getRealisedPnl()).thenReturn(10L);
        when(statistics.getPositionMargin()).thenReturn(10L);
        when(propertiesFacade.getMaxInvest()).thenReturn(maxToInvest);
        when(propertiesFacade.getPercentageToInvest()).thenReturn(90L);

        assertEquals(maxToInvest, simpleOrderQuantityCalculator.satoshisToInvest());
    }
}