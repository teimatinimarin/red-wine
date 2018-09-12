package com.beuwa.redwine.sensor.app;

import com.beuwa.redwine.sensor.config.PropertiesFacade;
import com.beuwa.redwine.sensor.utils.Signer;
import com.beuwa.redwine.sensor.utils.SubscribeUtils;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.inject.Inject;
import javax.websocket.CloseReason;
import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WebClientListenerTest {
    @InjectMocks
    WebClientListener webClientListener;

    @Mock
    Logger logger;

    @Mock
    PropertiesFacade propertiesFacade;

    @Mock
    Signer signer;

    @Mock
    MessageProcessor messageProcessor;

    @Mock
    SubscribeUtils subscribeUtils;

    @Test
    void onOpen() throws Exception {
        Session session = Mockito.mock(Session.class);
        RemoteEndpoint.Basic basic = Mockito.mock( RemoteEndpoint.Basic.class );
        when(session.getBasicRemote()).thenReturn(basic);
        webClientListener.onOpen( session );
    }

    @Test
    void onMessage() {
        webClientListener.onMessage("Message");

        verify(logger, times(1)).info( anyString() );
        verify(messageProcessor, times(1)).process( anyString() );
    }

    @Test
    void onClose() {
        CloseReason closeReason = Mockito.mock(CloseReason.class);
        when(closeReason.getCloseCode()).thenReturn(CloseReason.CloseCodes.CLOSED_ABNORMALLY);
        when(closeReason.getReasonPhrase()).thenReturn("Unexpected closed");

        webClientListener.onClose(closeReason);
    }
}