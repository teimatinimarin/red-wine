package com.beuwa.redwine.sensor;

import org.junit.jupiter.api.Test;

class WebClientListenerTest {

    @Test
    void onOpen() {
        new WebClientListener().onOpen();
    }

    @Test
    void onMessage() {
        new WebClientListener().onMessage("Message");
    }

    @Test
    void onClose() {
        new WebClientListener().onClose();
    }
}