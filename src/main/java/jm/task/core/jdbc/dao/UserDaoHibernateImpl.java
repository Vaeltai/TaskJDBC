package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

import static java.sql.Connection.TRANSACTION_READ_COMMITTED;

public class UserDaoHibernateImpl implements UserDao {
//    private Session session;
//    private Transaction transaction;
    public UserDaoHibernateImpl() {}

    @Override
    public void createUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
//            Session session = Util.getSessionFactory().openSession();
//            transaction = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE if NOT EXISTS Users " +
                    "(id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                    "user_name VARCHAR(255) NOT NULL, " +
                    "last_name VARCHAR(255) NOT NULL, " +
                    "age int NOT NULL);").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.out.println("exeption in createUsersTable");
            if(transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS Users;").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.out.println("exeption in dropUsersTable");
            if(transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
//            Session session = Util.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(new User (name, lastName, age));
            System.out.println("User " + name + " added to database");
            transaction.commit();
        } catch (Exception e) {
            if(transaction != null) {
                transaction.rollback();
            }
            System.out.println("exeption in saveUser");
        } finally {
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
//            session = Util.getSessionFactory().openSession();

            transaction = session.beginTransaction();
            User user = (User) session.get(User.class, id);
            if (user != null) {
                session.delete(user);
            }
//            session.createSQLQuery("DELETE from Users WHERE id = " + id + ";")
//                    .executeUpdate();
        } catch (Exception e) {
            System.out.println("exeption in removeUserById");
            if(transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
//            session = Util.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            usersList = (List<User>) session.createQuery("From User").list();
            transaction.commit();
        } catch (Exception e) {
            System.out.println("exeption in getAllUsers");
            if(transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
        return usersList;
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
//            session = Util.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.out.println("exeption in cleanUsersTable");
            if(transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
    }
}