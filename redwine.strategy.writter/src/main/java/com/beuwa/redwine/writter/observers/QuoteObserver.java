package com.beuwa.redwine.writter.observers;

import com.beuwa.redwine.core.events.business.QuoteEvent;
import com.beuwa.redwine.core.services.DatabaseService;
import org.apache.logging.log4j.Logger;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Instant;

public class QuoteObserver {
    @Inject
    Logger logger;

    @Inject
    DatabaseService databaseService;

    public void observe(@Observes QuoteEvent event) throws SQLException {
        logger.debug(
                "QuoteEvent: {}",
                event.getMessage()
        );

        long init = Instant.now().toEpochMilli();
        Connection connection = databaseService.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT IGNORE INTO redwine.trades (epoch, message) VALUES(?, ?)");
        long epoch = event.getEpoch();
        preparedStatement.setLong(1, epoch);
        preparedStatement.setString(2, event.getMessage());
        preparedStatement.execute();
        long finished = Instant.now().toEpochMilli();
        logger.info("Took... {}", finished-init);

    }
}

