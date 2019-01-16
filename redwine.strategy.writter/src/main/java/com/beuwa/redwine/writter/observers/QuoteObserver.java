package com.beuwa.redwine.writter.observers;

import com.beuwa.redwine.core.events.business.QuoteEvent;
import com.beuwa.redwine.core.services.DatabaseService;
import com.beuwa.redwine.writter.utils.WritterUtils;
import org.apache.logging.log4j.Logger;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.sql.SQLException;
import java.time.Instant;

public class QuoteObserver {
    @Inject
    Logger logger;

    @Inject
    WritterUtils writterUtils;

    public void observe(@Observes QuoteEvent event) throws SQLException {
        logger.debug(
                "QuoteEvent: {}",
                event.getMessage()
        );

        long init = Instant.now().toEpochMilli();
        writterUtils.insertQuote(event.getEpoch(), event.getMessage());
        long finished = Instant.now().toEpochMilli();

        logger.info("Took... {}", finished-init);
    }
}

