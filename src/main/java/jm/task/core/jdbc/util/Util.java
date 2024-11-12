package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String url = "jdbc:mysql://localhost:3306/mydb";
    private static final String user = "root";
    private static final String password = "root";

    public Connection getConnection() throws SQLException {
        Connection connection = null;
        connection = DriverManager.getConnection(url, user, password);
        return connection;
    }
}