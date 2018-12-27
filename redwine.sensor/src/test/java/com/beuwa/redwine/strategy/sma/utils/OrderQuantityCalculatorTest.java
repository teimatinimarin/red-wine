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
class OrderQuantityCalculatorTest {
    @InjectMocks
    OrderQuantityCalculator orderQuantityCalculator;

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
        when(propertiesFacade.getMaxInvest()).thenReturn(50);
        when(propertiesFacade.getPercentageToInvest()).thenReturn(90L);

        //assertEquals(900009L, orderQuantityCalculator.satoshisToInvest());
    }

    @Test
    void satoshisToInvestMaxToInvest() {
        var maxToInvest = 50;
        when(statistics.getWalletBalance()).thenReturn(1000010L);
        when(statistics.getRealisedPnl()).thenReturn(10L);
        when(statistics.getPositionMargin()).thenReturn(10L);
        when(propertiesFacade.getMaxInvest()).thenReturn(maxToInvest);
        when(propertiesFacade.getPercentageToInvest()).thenReturn(90L);

        //assertEquals(maxToInvest, orderQuantityCalculator.satoshisToInvest());
    }
}