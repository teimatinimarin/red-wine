package com.beuwa.redwine.sensor.producers;

import org.glassfish.tyrus.client.ClientManager;

import javax.enterprise.inject.Produces;

public class ClientManagerProducer {
    @Produces
    ClientManager createWebSocketClient() {
        return ClientManager.createClient();
    }
}
