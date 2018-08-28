package com.beuwa.redwine.sensor.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URI;

@ExtendWith(MockitoExtension.class)
class PropertiesFacadeTest {
    @InjectMocks
    PropertiesFacade propertiesFacade;

    @Mock
    Properties properties;

    @Test
    void getEndpoint() throws Exception {
        Mockito.when(properties.getEndpoint()).thenReturn( "domain.com" );
        URI uri = propertiesFacade.buildEndpoint();
        System.out.println(uri);
    }
}