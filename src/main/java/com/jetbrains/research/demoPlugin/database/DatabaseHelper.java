package com.jetbrains.research.demoPlugin.database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import com.intellij.openapi.diagnostic.Logger;
import com.jetbrains.research.demoPlugin.model.UserFeedback;

/**
 * Handles database operations for the demo plugin.
 */
public class DatabaseHelper {
    private static final Logger logger = Logger.getInstance(DatabaseHelper.class);
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
            throw new RuntimeException("Failed to load database properties", ex);
        }

        String dbUrl = System.getenv("DEMO_PLUGIN_DB_URL");
        if (dbUrl == null || dbUrl.isEmpty()) {
            dbUrl = properties.getProperty("db.url");
        }
        DB_URL = dbUrl;
        logger.info("Database URL: " + DB_URL);
    }

    private static final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS feedback (id INTEGER PRIMARY KEY AUTOINCREMENT, feedback TEXT NOT NULL, animal_type TEXT, rating INTEGER)";
    private static final String INSERT_FEEDBACK_SQL = "INSERT INTO feedback(feedback, animal_type, rating) VALUES(?, ?, ?)";

    public DatabaseHelper() {
        try {
            Class.forName("org.sqlite.JDBC"); // Ensure the SQLite JDBC driver is loaded
            createTableIfNotExists();
        } catch (ClassNotFoundException | SQLException e) {
            logger.error("Failed to initialize DatabaseHelper", e);        }
    }

    private void createTableIfNotExists() throws SQLException {
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(CREATE_TABLE_SQL)) {
            pstmt.executeUpdate();
        }
    }

    private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
        }
        return conn;
    }

    /**
     * Inserts feedback into the database.
     * @param userFeedback The user feedback
     *
     */
    public void insertFeedback(UserFeedback userFeedback) {
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(INSERT_FEEDBACK_SQL)) {
            pstmt.setString(1, userFeedback.getFeedback());
            pstmt.setString(2, userFeedback.getAnimalType());
            pstmt.setInt(3, userFeedback.getRating());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error inserting feedback", e);
        }
    }
}
