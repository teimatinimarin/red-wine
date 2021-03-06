package com.beuwa.redwine.core.producers;

import javax.enterprise.inject.Produces;
import java.net.http.HttpClient;
import java.net.http.WebSocket;

public class WebSocketBuilderProducer {
    @Produces
     WebSocket.Builder createWebSocketClient() {
        return HttpClient.newHttpClient().newWebSocketBuilder();
    }
}
