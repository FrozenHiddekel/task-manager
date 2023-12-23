package Gihon.task.manager.util;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DevOnly {
    public static void dbInit(){
        FileInputStream fis;
        Properties properties = new Properties();
        try {
            fis = new FileInputStream("src/main/resources/application.properties");
            properties.load(fis);
            String USER = properties.getProperty("spring.datasource.username");
            String DB_URL = properties.getProperty("database.url");
            String DB_NAME = properties.getProperty("database.name");
            String PASS = properties.getProperty("spring.datasource.password");
            try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement stmt = conn.createStatement();
            ) {
                String sql = "CREATE DATABASE " + DB_NAME;
                stmt.executeUpdate(sql);
                System.out.println("Database created successfully...");
            } catch (SQLException e) {
                System.out.println("Database has already been created...");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
