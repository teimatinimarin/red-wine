package com.beuwa.redwine.strategy.rollercoaster.observers;

import com.beuwa.redwine.core.events.business.TradeEvent;
import com.beuwa.redwine.core.model.ModelFactory;
import com.beuwa.redwine.core.model.Trades;
import com.beuwa.redwine.core.services.OrderbookService;
import org.apache.logging.log4j.Logger;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.time.Instant;

public class TradeObserver {
    @Inject
    Logger logger;

    @Inject
    private ModelFactory modelFactory;

    @Inject
    private OrderbookService orderbookService;

    public void observe(@Observes TradeEvent event) {
        long init = Instant.now().toEpochMilli();
        Trades trades = modelFactory.buildTrade(event.getMessage());
        long finished = Instant.now().toEpochMilli();

        if(orderbookService.isInited()) {
            orderbookService.print(60);
        }

        logger.debug(
                "TradeEvent ({}): {}",
                finished-init,
                trades.getAction()
        );
    }
}

