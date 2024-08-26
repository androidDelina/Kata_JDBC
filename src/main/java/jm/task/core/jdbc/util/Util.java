package jm.task.core.jdbc.util;

import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static Connection connection = null;

    private static final String URL = "jdbc:mysql://localhost:3306/mydbtest";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "27112001";

    public static Connection getConnectionJDBC() {
        try {
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    public static Session getSessionHibernate() {

        Configuration configuration = new Configuration()
                .setProperty("hibernate.connection.url", URL)
                .setProperty("connection.driver_class", "com.mysql.cj.jdbc.Driver");


        return null;
    }
}
