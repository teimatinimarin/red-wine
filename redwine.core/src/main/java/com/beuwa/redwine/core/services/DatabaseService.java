package com.beuwa.redwine.core.services;

import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Singleton
public class DatabaseService {
    private Connection connection;

    public Connection getConnection() throws SQLException {
        if(null == connection) {
            connection = DriverManager.getConnection("jdbc:postgresql://fr1hm4v7whgtd1a.cwzeacqdfykx.eu-west-1.rds.amazonaws.com:5432/fr1hm4v7whgtd1a", "redwine", "1122334455");
        }

        return connection;
    }

    public void execute(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.execute();
    }
}
