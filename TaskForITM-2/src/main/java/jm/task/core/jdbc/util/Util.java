package jm.task.core.jdbc.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String PROPERTIES_FILE = "db.properties";
    private static Connection connection ;
    private static Util instanse = null;

    private Util() {
        try {
            if (connection == null || connection.isClosed()) {

                Properties prop = new Properties();

                prop.load(Util.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE));
                Class.forName(prop.getProperty("db.driver"));
                connection = DriverManager.getConnection(
                        prop.getProperty("db.url"),
                        prop.getProperty("db.username"),
                        prop.getProperty("db.password"));
            }

        } catch (IOException | SQLException | RuntimeException|ClassNotFoundException e) {
            e.printStackTrace();


        }
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

    public static Connection getConnection() {
        return connection;
    }

}
