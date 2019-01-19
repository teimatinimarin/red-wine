package com.beuwa.redwine.writter.observers;

import com.beuwa.redwine.core.events.business.TradeEvent;
import com.beuwa.redwine.writter.utils.WritterUtils;
import org.apache.logging.log4j.Logger;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.sql.SQLException;
import java.time.Instant;

public class TradeObserver {
    @Inject
    Logger logger;

    @Inject
    WritterUtils writterUtils;

    public void observe(@Observes TradeEvent event) {
        long init = Instant.now().toEpochMilli();
        try {
            writterUtils.insertTrade(event.getEpoch(), event.getMessage());
        } catch (SQLException sqlE) {
            logger.warn(sqlE.getMessage());
        }
        long finished = Instant.now().toEpochMilli();

        logger.debug(
                "TradeEvent ({}): {}",
                finished-init,
                event.getMessage()
        );
    }
}

