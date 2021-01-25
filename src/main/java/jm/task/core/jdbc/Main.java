package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
        UserDao userDao = new UserDaoHibernateImpl();
        userDao.createUsersTable();
        userDao.saveUser("Anna", "Petrova", (byte) 22);
        userDao.saveUser("Alexey", "Ivanov", (byte) 26);
        userDao.saveUser("Oleg", "Smirnov", (byte) 35);
        userDao.saveUser("Marina", "Sidorova", (byte) 46);
        System.out.println(userDao.getAllUsers().toString());
        userDao.removeUserById(1);
        System.out.println(userDao.getAllUsers().toString());
        userDao.cleanUsersTable();
        System.out.println(userDao.getAllUsers().toString());
        userDao.saveUser("Anna", "Petrova", (byte) 22);
        System.out.println(userDao.getAllUsers().toString());
        userDao.dropUsersTable();
        System.out.println("done");
        Util.getSessionFactory().close();
    }
}
