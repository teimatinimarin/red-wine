package com.beuwa.redwine.core.producers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

public class LoggerProducer {
    @Produces
    Logger createLogger(InjectionPoint injectionPoint) {
        //return LogManager.getLogger( injectionPoint.getMember().getDeclaringClass().getName() );
        return LogManager.getFormatterLogger( injectionPoint.getMember().getDeclaringClass().getName() );
    }
}
