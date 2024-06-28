package com.jetbrains.research.demoPlugin.database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseHelper {
    private static final Properties properties = new Properties();
    private static final String DB_URL;

    static {
        try (InputStream input = DatabaseHelper.class.getClassLoader().getResourceAsStream("database.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find database.properties");
                throw new RuntimeException("database.properties not found");
            }

            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        DB_URL = properties.getProperty("db.url");
    }

    private static final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS feedback (id INTEGER PRIMARY KEY AUTOINCREMENT, feedback TEXT NOT NULL, animal_type TEXT NOT NULL, rating INTEGER NOT NULL)";
    private static final String INSERT_FEEDBACK_SQL = "INSERT INTO feedback(feedback, animal_type, rating) VALUES(?, ?, ?)";

    public DatabaseHelper() {
        try {
            Class.forName("org.sqlite.JDBC"); // Ensure the SQLite JDBC driver is loaded
            try (Connection conn = this.connect();
                 PreparedStatement pstmt = conn.prepareStatement(CREATE_TABLE_SQL)) {
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Error creating table: " + e.getMessage());
            }
        } catch (ClassNotFoundException e) {
            System.out.println("SQLite JDBC driver not found: " + e.getMessage());
        }
    }

    private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
        return conn;
    }

    public void insertFeedback(String feedback, String animalType, int rating) {
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(INSERT_FEEDBACK_SQL)) {
            pstmt.setString(1, feedback);
            pstmt.setString(2, animalType);
            pstmt.setInt(3, rating);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error inserting feedback: " + e.getMessage());
        }
    }
}

