package com.beuwa.redwine.writter.utils;

import com.beuwa.redwine.core.services.DatabaseService;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WritterUtils {
    private final static String INSERT_TRADE = "INSERT INTO redwine.trades (epoch, message) VALUES(?, ?::json) ON CONFLICT DO NOTHING";
    private final static String INSERT_ORDERBOOK = "INSERT INTO redwine.ORDERBOOK (epoch, message) VALUES(?, ?::json) ON CONFLICT DO NOTHING";

    @Inject
    DatabaseService databaseService;

    public void insertTrade(long epoch, String message) throws SQLException {
        Connection connection = databaseService.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TRADE)) {
            preparedStatement.setLong(1, epoch);
            preparedStatement.setString(2, message);
            preparedStatement.execute();
        }
    }

    public void insertOrderbook(long epoch, String message) throws SQLException {
        Connection connection = databaseService.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ORDERBOOK)) {
            preparedStatement.setLong(1, epoch);
            preparedStatement.setString(2, message);
            preparedStatement.execute();
        }
    }
}
