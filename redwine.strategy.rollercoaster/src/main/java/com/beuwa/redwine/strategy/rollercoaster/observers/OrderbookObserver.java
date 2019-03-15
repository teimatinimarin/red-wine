package com.beuwa.redwine.strategy.rollercoaster.observers;

import com.beuwa.redwine.core.events.business.OrderbookEvent;
import com.beuwa.redwine.core.model.ModelFactory;
import com.beuwa.redwine.core.model.Orderbook;
import com.beuwa.redwine.core.services.OrderbookService;
import org.apache.logging.log4j.Logger;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.time.Instant;

public class OrderbookObserver {
    @Inject
    private Logger logger;

    @Inject
    private ModelFactory modelFactory;

    @Inject
    private OrderbookService orderbookService;

    public void observe(@Observes OrderbookEvent event) {
        long init = Instant.now().toEpochMilli();
        Orderbook orderbookDelta = modelFactory.buildOrderbook(event.getMessage());
        orderbookService.process(orderbookDelta);
        long fin = Instant.now().toEpochMilli();
        long total = fin - init;

        logger.info(
                "OrderbookEvent (%d): %s",
                total,
                event.getMessage()
        );
        //orderbookService.print();
    }
}

