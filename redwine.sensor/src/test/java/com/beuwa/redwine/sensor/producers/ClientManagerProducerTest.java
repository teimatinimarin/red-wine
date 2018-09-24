package com.beuwa.redwine.sensor.producers;

import org.glassfish.tyrus.client.ClientManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ClientManagerProducerTest {
    @InjectMocks
    ClientManagerProducer clientManagerProducer;

    @Test
    void createWebSocketClient() {
        ClientManager client = clientManagerProducer.createWebSocketClient();
        assertNotNull(client);
    }
}