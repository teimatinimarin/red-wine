package com.beuwa.redwine.sensor.app;

import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WebClientListenerTest {
    @InjectMocks
    WebClientListener webClientListener;

    @Mock
    Logger logger;

    @Test
    void onOpen() {
        webClientListener.onOpen();
    }

    @Test
    void onMessage() {
        webClientListener.onMessage("Message");
    }

    @Test
    void onClose() {
        webClientListener.onClose();
    }
}