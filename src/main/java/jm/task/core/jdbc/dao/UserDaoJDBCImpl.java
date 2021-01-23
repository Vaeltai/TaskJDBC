package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Connection.TRANSACTION_READ_COMMITTED;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }
    public void createUsersTable() {
        try (Statement statement = Util.getConnectionToDatabase().createStatement()){
            statement.executeUpdate("CREATE TABLE if NOT EXISTS Users (id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                    "user_name VARCHAR(255) NOT NULL, " +
                    "last_name VARCHAR(255) NOT NULL, " +
                    "age int NOT NULL);");
            Util.getConnectionToDatabase().commit();
        } catch (SQLException | ClassNotFoundException ignore) {
            //ignore
        }
    }

    public void dropUsersTable() {
        try (Statement statement = Util.getConnectionToDatabase().createStatement()){
            statement.executeUpdate("drop table Users;");
            Util.getConnectionToDatabase().commit();
        } catch (SQLException | ClassNotFoundException ignore) {
            //ignore
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Statement statement = Util.getConnectionToDatabase().createStatement()) {
            statement.executeUpdate("insert Users (user_name, last_name, age) values " +
                    "(\'" + name + "\', \'" + lastName +"\', " + age + ");");
            System.out.println("User " + name + " added to database");
            Util.getConnectionToDatabase().commit();
        } catch (SQLException | ClassNotFoundException ignore) {
            //ignore
        }
    }

    public void removeUserById(long id) {
        try (Statement statement = Util.getConnectionToDatabase().createStatement()) {
            statement.executeUpdate("DELETE FROM Users WHERE id = " + id + ";");
            Util.getConnectionToDatabase().commit();
        } catch (SQLException | ClassNotFoundException ignore) {
            //ignore
        }
    }

    public List<User> getAllUsers() {

            List<User> usersList = new ArrayList<>();
            ResultSet resultSet = null;
            try (Statement statement = Util.getConnectionToDatabase().createStatement()) {
                resultSet = statement.executeQuery("SELECT * FROM Users;");

                while (resultSet.next()) {
                     usersList.add(new User(resultSet.getString("user_name"),
                            resultSet.getString("last_name"), resultSet.getByte("age")));
                }

                Util.getConnectionToDatabase().commit();
            } catch (SQLException | ClassNotFoundException ignore) {
                //ignore
            } finally {
                try {
                    resultSet.close();
                } catch (SQLException e) {

                }
            }
            return usersList;
    }

    public void cleanUsersTable() {
        try(Statement statement = Util.getConnectionToDatabase().createStatement()) {
            statement.executeUpdate("TRUNCATE TABLE users;");
            Util.getConnectionToDatabase().commit();
        } catch (SQLException | ClassNotFoundException ignore) {
            //ignore
        }
    }
}
