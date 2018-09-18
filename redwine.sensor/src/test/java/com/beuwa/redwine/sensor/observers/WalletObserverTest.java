package com.beuwa.redwine.sensor.observers;

import com.beuwa.redwine.core.events.WalletEvent;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class WalletObserverTest {
    @InjectMocks
    WalletObserver walletObserver;

    @Mock
    Logger logger;

    @Test
    void observe() {
        WalletEvent walletEvent = mock(WalletEvent.class);
        walletObserver.observe(walletEvent);
    }
}