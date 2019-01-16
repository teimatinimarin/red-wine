package com.beuwa.redwine.sensor.app;

import com.beuwa.redwine.core.config.PropertiesFacade;
import com.beuwa.redwine.core.utils.Signer;
import com.beuwa.redwine.core.utils.SubscribeUtils;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class WebClientListenerTest {
    @InjectMocks
    WebSocketClientListener webSocketClientListener;

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

}