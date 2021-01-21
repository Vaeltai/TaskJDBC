package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;

public class Main {
    public static void main(String[] args) {
        UserDaoJDBCImpl userDaoJDBC = new UserDaoJDBCImpl();
        userDaoJDBC.createUsersTable();
        userDaoJDBC.saveUser("Anna", "Petrova", (byte) 22);
        userDaoJDBC.saveUser("Alexey", "Ivanov", (byte) 26);
        userDaoJDBC.saveUser("Oleg", "Smirnov", (byte) 35);
        userDaoJDBC.saveUser("Marina", "Sidorova", (byte) 46);
        System.out.println(userDaoJDBC.getAllUsers().toString());
        userDaoJDBC.cleanUsersTable();
        userDaoJDBC.dropUsersTable();
    }
}
