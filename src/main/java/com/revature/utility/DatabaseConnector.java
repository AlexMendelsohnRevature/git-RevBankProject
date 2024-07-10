package com.revature.utility;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {

    public static Connection createConnection() throws SQLException
    {
        return DriverManager.getConnection("jdbc:sqlite:src/main/resources/bank.db");
    }
}
