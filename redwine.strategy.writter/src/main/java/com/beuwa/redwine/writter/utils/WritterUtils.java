package com.beuwa.redwine.writter.utils;

import com.beuwa.redwine.core.services.DatabaseService;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WritterUtils {
    private final static String QUERY = "INSERT INTO redwine.quotes (epoch, message) VALUES(?, ?::json) ON CONFLICT DO NOTHING";

    @Inject
    DatabaseService databaseService;

    public void insertQuote(long epoch, String message) throws SQLException {
        Connection connection = databaseService.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(QUERY)) {
            preparedStatement.setLong(1, epoch);
            preparedStatement.setString(2, message);
            preparedStatement.execute();
        }
    }
}

