package com.beuwa.redwine.sensor.app;

import com.beuwa.redwine.core.config.PropertiesFacade;
import com.beuwa.redwine.core.events.BootEvent;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.http.WebSocket;

@ExtendWith(MockitoExtension.class)
class InitializerTest {
    @InjectMocks
    Initializer initializer;

    @Mock
    private Logger LOGGER;

    @Mock
    private PropertiesFacade propertiesFacade;

    @Mock
    WebSocket.Builder websocketBuilder;

    @Mock
    WebSocketClientListener listener;

    /*
    @Test
    void connectToServerOk() throws Exception {
        when(propertiesFacade.getWssEndpoint()).thenReturn(new URI("wss://websocket.org"));
        CompletableFuture future = new CompletableFuture<>();
        future.complete("completed");
        when(websocketBuilder.buildAsync(any(URI.class), any(WebSocketClientListener.class))).thenReturn(future);
        CountDownLatch mockedLatch = Mockito.mock(CountDownLatch.class);
        initializer.init(new BootEvent(), mockedLatch);

        verify(propertiesFacade, times(1)).getWssEndpoint();
        verify(websocketBuilder, times(1)).buildAsync(any(URI.class), any(WebSocketClientListener.class));
        verify(mockedLatch, times(1)).await();
    }

    @Test
    void connectToServerURISyntaxException() throws Exception {
        when(propertiesFacade.getWssEndpoint()).thenThrow(URISyntaxException.class);

        CountDownLatch mockedLatch = Mockito.mock(CountDownLatch.class);
        initializer.init(new BootEvent(), mockedLatch);

        verify(propertiesFacade, times(1)).getWssEndpoint();
        verify(websocketBuilder, times(0)).buildAsync(any(URI.class), any(WebSocketClientListener.class));
        verify(mockedLatch, times(0)).await();
    }
    */
}