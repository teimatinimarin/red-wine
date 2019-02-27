package com.beuwa.redwine.strategy.rollercoaster.utils;

import com.beuwa.redwine.core.services.DatabaseService;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DbUtils {
    private final static String INSERT_TRADE = "INSERT INTO redwine.trades (epoch, message) VALUES(?, ?::json) ON CONFLICT DO NOTHING";
    private final static String EPOCH_ASCENDING = "select epoch from redwine.trades order by epoch asc";

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
}

