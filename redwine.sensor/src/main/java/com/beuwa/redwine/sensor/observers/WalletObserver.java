package com.beuwa.redwine.sensor.observers;

import com.beuwa.redwine.sensor.events.WalletEvent;
import org.apache.logging.log4j.Logger;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

public class WalletObserver {
    @Inject
    Logger logger;

    public void observe(@Observes WalletEvent event) {
        logger.info("Wallet amount: {}", event.getAmount());
    }
}
