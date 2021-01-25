package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static SessionFactory sessionFactory;
    private static final String url = "jdbc:mysql://localhost:3306/first";
    private static final String userName = "root";
    private static final String password = "123456789";
    public static Connection getConnectionToDatabase() throws SQLException, ClassNotFoundException {
        Connection connectionToDatabase = DriverManager.getConnection(url, userName, password);
        Class.forName("com.mysql.jdbc.Driver");

        return connectionToDatabase;
    }

    public static SessionFactory getSessionFactory() {
        if(sessionFactory == null) {
            Configuration configurationForResult = new Configuration();
            Properties setting = new Properties();
            setting.put(Environment.DRIVER, "com.mysql.jdbc.Driver");
            setting.put(Environment.URL, url);
            setting.put(Environment.USER, userName);
            setting.put(Environment.PASS, password);
            setting.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
            setting.put(Environment.SHOW_SQL, "true");
            setting.put(Environment.HBM2DDL_AUTO, "update");

            setting.put(Environment.POOL_SIZE, 1);
            setting.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

            configurationForResult.setProperties(setting); // применить настройки
            configurationForResult.addAnnotatedClass(User.class); // добавить entity class
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configurationForResult.getProperties()).build();
            sessionFactory = configurationForResult.buildSessionFactory(serviceRegistry);
        }

        return sessionFactory;
    }
}
