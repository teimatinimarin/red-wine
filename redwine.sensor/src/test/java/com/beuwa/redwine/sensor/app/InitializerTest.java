package com.beuwa.redwine.sensor.app;

import com.beuwa.redwine.sensor.config.PropertiesFacade;
import com.beuwa.redwine.sensor.events.BootEvent;
import org.apache.logging.log4j.Logger;
import org.glassfish.tyrus.client.ClientManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.websocket.DeploymentException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InitializerTest {
    @InjectMocks
    Initializer initializer;

    @Mock
    private Logger LOGGER;

    @Mock
    private PropertiesFacade propertiesFacade;

    @Mock
    private ClientManager client;

    @Mock
    WebClientListener listener;

    @Test
    void connectToServerOk() throws Exception {
        CountDownLatch mockedLatch = Mockito.mock(CountDownLatch.class);
        initializer.init(new BootEvent(), mockedLatch);

        verify(propertiesFacade, times(1)).buildEndpoint();
        verify(client, times(1)).connectToServer(any(WebClientListener.class), isNull());
        verify(mockedLatch, times(1)).await();
    }

    @Test
    void connectToServerDeploymentException() throws Exception {
        when(client.connectToServer(listener, null)).thenThrow(DeploymentException.class);

        CountDownLatch mockedLatch = Mockito.mock(CountDownLatch.class);
        initializer.init(new BootEvent(), mockedLatch);

        verify(propertiesFacade, times(1)).buildEndpoint();
        verify(client, times(1)).connectToServer(any(WebClientListener.class), isNull());
        verify(mockedLatch, times(0)).await();
    }

    @Test
    void connectToServerIOException() throws Exception {
        when(client.connectToServer(listener, null)).thenThrow(IOException.class);

        CountDownLatch mockedLatch = Mockito.mock(CountDownLatch.class);
        initializer.init(new BootEvent(), mockedLatch);

        verify(propertiesFacade, times(1)).buildEndpoint();
        verify(client, times(1)).connectToServer(any(WebClientListener.class), isNull());
        verify(mockedLatch, times(0)).await();
    }

    @Test
    void connectToServerURISyntaxException() throws Exception {
        when(propertiesFacade.buildEndpoint()).thenThrow(URISyntaxException.class);

        CountDownLatch mockedLatch = Mockito.mock(CountDownLatch.class);
        initializer.init(new BootEvent(), mockedLatch);

        verify(propertiesFacade, times(1)).buildEndpoint();
        verify(client, times(0)).connectToServer(any(WebClientListener.class), isNull());
        verify(mockedLatch, times(0)).await();
    }
}