package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static java.sql.Connection.TRANSACTION_READ_COMMITTED;

public class Util {

    public static Connection getConnectionToDatabase() throws SQLException, ClassNotFoundException {
        String url = "jdbc:mysql://localhost:3306/first";
        String userName = "root";
        String password = "123456789";
        Connection connectionToDatabase = DriverManager.getConnection(url, userName, password);
        Class.forName("com.mysql.jdbc.Driver");
        connectionToDatabase.setTransactionIsolation(TRANSACTION_READ_COMMITTED);
        connectionToDatabase.setAutoCommit(false);

        return connectionToDatabase;
    }

}
