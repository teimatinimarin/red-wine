package com.beuwa.redwine.sensor.producers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.http.WebSocket;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class WebSocketBuilderProducerTest {
    @InjectMocks
    WebSocketBuilderProducer clientManagerProducer;

    @Test
    void createWebSocketClient() {
        WebSocket.Builder client = clientManagerProducer.createWebSocketClient();
        assertNotNull(client);
    }
}