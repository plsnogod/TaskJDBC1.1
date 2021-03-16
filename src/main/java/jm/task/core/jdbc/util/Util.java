package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory(){
        Properties prop = new Properties();
        prop.setProperty("hibernate.connection.url", URL);
        prop.setProperty("hibernate.connection.username",USERNAME);
        prop.setProperty("hibernate.connection.password", PASSWORD);
        prop.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        prop.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        if (sessionFactory == null) {
            sessionFactory = new Configuration()
                    .addProperties(prop)
                    .addAnnotatedClass(User.class)
                    .buildSessionFactory();
        }
        return sessionFactory;
    }


    public static void closeSessionFactory(){
        getSessionFactory().close();
    }


    private static final String URL = "jdbc:mysql://localhost:3306/coretaskjdbc?useSSL=false";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public static Connection getConnectionJDBC() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            // System.out.println("Connection succeeded");
        } catch (SQLException e) {
            System.out.println("Connection failed");
        }
        return connection;
    }
}
