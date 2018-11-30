package com.beuwa.redwine.sensor.observers;

import com.beuwa.redwine.core.events.business.WalletEvent;
import org.apache.logging.log4j.Logger;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

public class WalletObserver {
    @Inject
    Logger logger;

    public void observe(@Observes WalletEvent event) {
        logger.debug("Wallet amount: {}", event.getAmount());
    }
}
