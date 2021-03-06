package com.beuwa.redwine.writter.observers;

import com.beuwa.redwine.core.events.business.OrderbookEvent;
import com.beuwa.redwine.core.model.ModelFactory;
import com.beuwa.redwine.core.model.Orderbook;
import com.beuwa.redwine.core.services.OrderbookService;
import com.beuwa.redwine.writter.utils.WritterUtils;
import org.apache.logging.log4j.Logger;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.sql.SQLException;
import java.time.Instant;

public class OrderbookObserver {
    @Inject
    private Logger logger;

    @Inject
    private ModelFactory modelFactory;

    @Inject
    private OrderbookService orderbookService;

    @Inject
    WritterUtils writterUtils;

    public void observe(@Observes OrderbookEvent event) {
        long init = Instant.now().toEpochMilli();
        try {
            writterUtils.insertOrderbook(init, event.getMessage());
        } catch (SQLException sqlE) {
            logger.warn(sqlE.getMessage());
        }
        long finished = Instant.now().toEpochMilli();

        logger.debug(
                "OrderbookEvent ({}): {}",
                finished-init,
                event.getMessage()
        );
    }
}

