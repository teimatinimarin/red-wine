package com.beuwa.redwine.sensor.app;

import com.beuwa.redwine.core.events.BusinessEvent;
import com.beuwa.redwine.core.events.EventsFactory;

import org.apache.logging.log4j.Logger;

import javax.enterprise.event.Event;
import javax.inject.Inject;

public class MessageProcessor {
    @Inject
    private Logger logger;

    @Inject
    private EventsFactory eventsFactory;

    @Inject
    private Event<BusinessEvent> event;

    public void process(String message) {
        logger.debug(message);
        BusinessEvent[] businessEvents = eventsFactory.build(message);
        if(businessEvents != null) {
            for(BusinessEvent businessEvent:businessEvents) {
                if(businessEvent != null) {
                    event.fire(businessEvent);
                }
            }
        }
    }
}
