package com.beuwa.redwine.writter.utils;

import com.beuwa.redwine.core.services.DatabaseService;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WritterUtils {
    @Inject
    DatabaseService databaseService;

    public void insertQuote(long epoch, String message) throws SQLException {
        Connection connection = databaseService.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT IGNORE INTO redwine.trades (epoch, message) VALUES(?, ?)");
        preparedStatement.setLong(1, epoch);
        preparedStatement.setString(2, message);
        preparedStatement.execute();
    }
}

