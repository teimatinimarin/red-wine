package com.beuwa.redwine.core.producers;

import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.enterprise.inject.spi.InjectionPoint;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoggerProducerTest {
    @InjectMocks
    LoggerProducer loggerProducer;

    @Test
    void createLogger() throws Exception {
        InjectionPoint injectionPoint = mock(InjectionPoint.class);
        Method[] methods = this.getClass().getMethods(); // Methods are also Member instances
        when( injectionPoint.getMember() ).thenReturn( methods[0]);

        Logger logger = loggerProducer.createLogger(injectionPoint);
        assertNotNull(logger);
    }
}