package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

import static java.sql.Connection.TRANSACTION_READ_COMMITTED;

public class UserDaoHibernateImpl implements UserDao {
    private Session session;
    private Transaction transaction;
    public UserDaoHibernateImpl() {}

    @Override
    public void createUsersTable() {
        try {
            session = Util.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE if NOT EXISTS Users (id BIGINT AUTO_INCREMENT PRIMARY KEY, \" +\n" +
                    "\"user_name VARCHAR(255) NOT NULL, \" +\n" +
                    "\"last_name VARCHAR(255) NOT NULL, \" +\n" +
                    "\"age int NOT NULL);");
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void dropUsersTable() {
        try {
            session = Util.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS Users;");
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            session = Util.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.createSQLQuery("INSERT INTO USERS (user_name, last_name, age) VALUES " +
                    "('" + name + "', '" + lastName + "', " + age + ");")
            .executeUpdate();
            System.out.println("User " + name + " added to database");
            transaction.commit();
        } catch (Exception e) {
                transaction.rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
        session = Util.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        session.createSQLQuery("DELETE user WHERE id = " + id + ";")
        .executeUpdate();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> usersList = null;
        try {
            session = Util.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            usersList = (List<User>) session.createQuery("From User").list();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            session.close();
        }
        return usersList;
    }

    @Override
    public void cleanUsersTable() {
        try {
            session = Util.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.createSQLQuery("TRUNCATE TABLE users;").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            session.close();
        }
    }
}
