package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String PROPERTIES_FILE = "db.properties";
    private static Connection connection;
    private static Util instanse=null;
    private static Properties properties;
    private static SessionFactory sessionFactory;

    public Util() {
        properties = getProperties();
        initConnection();
    }



    public static Util getInstance() {
        if (instanse == null) {
            synchronized (Util.class) {
                if (instanse == null) {
                    instanse = new Util();
                }
            }
        }
        return instanse;
    }

    private static void initConnection() {
        try {
            Class.forName(properties.getProperty("db.driver"));
            connection = DriverManager.getConnection(
                    properties.getProperty("db.url"),
                    properties.getProperty("db.username"),
                    properties.getProperty("db.password"));
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                initConnection();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при подключении к базе данных");
        }
        return connection;
    }


public SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
             try {
                 Configuration config = new Configuration()
                         .setProperty("hibernate.connection.url", properties.getProperty("db.url"))
                         .setProperty("hibernate.connection.username", properties.getProperty("db.username"))
                         .setProperty("hibernate.connection.password", properties.getProperty("db.password"))
                         .setProperty("hibernate.dialect", properties.getProperty("db.hibernate.dialect"))
                         .setProperty("hibernate.hbm2ddl.auto", properties.getProperty("db.hibernate.hbm2ddl"))
                         .addAnnotatedClass(User.class);
                 sessionFactory = config.buildSessionFactory();
             } catch (Exception e) {
               e.printStackTrace();
                 System.out.println("Ошибка подключения к базе данных");
             }
        }
        return sessionFactory;
}

    private static Properties getProperties() {
        Properties properties = new Properties();
        try {
            properties.load(Util.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE));
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при загрузке свойств", e);

        }
        return properties;

    }
}
