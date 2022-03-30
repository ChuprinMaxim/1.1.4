package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

/*класс для подключения к БД*/
public class Util {
    private static SessionFactory sessionFactory;

    public static Connection getConnection() throws SQLException {
        String dbURL = "jdbc:mysql://localhost:3306/mydb";
        String dbUserName = "root";
        String dbPassword = "12345";
        return DriverManager.getConnection(dbURL, dbUserName, dbPassword);
    }

    /*----------------подключение к БД с помощью Hibernate------------------------------------------------------------------------*/
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties settings = new Properties();
                settings.put(Environment.URL, "jdbc:mysql://localhost:3306/mydb");
                settings.put(Environment.USER, "root");
                settings.put(Environment.PASS, "12345");
                configuration.setProperties(settings);
                configuration.addAnnotatedClass(User.class);
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
