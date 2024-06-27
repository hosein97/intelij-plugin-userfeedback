//package com.jetbrains.demoPlugin.database;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//
//public class DatabaseHelper {
//    private static final String DB_URL = "jdbc:sqlite:feedback.db";
//    private static final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS feedback (id INTEGER PRIMARY KEY AUTOINCREMENT, feedback TEXT NOT NULL)";
//    private static final String INSERT_FEEDBACK_SQL = "INSERT INTO feedback(feedback) VALUES(?)";
//
//    public DatabaseHelper() {
//        try (Connection conn = this.connect();
//             PreparedStatement pstmt = conn.prepareStatement(CREATE_TABLE_SQL)) {
//            pstmt.executeUpdate();
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//    private Connection connect() {
//        Connection conn = null;
//        try {
//            conn = DriverManager.getConnection(DB_URL);
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        return conn;
//    }
//
//    public void insertFeedback(String feedback) {
//        try (Connection conn = this.connect();
//             PreparedStatement pstmt = conn.prepareStatement(INSERT_FEEDBACK_SQL)) {
//            pstmt.setString(1, feedback);
//            pstmt.executeUpdate();
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//    }
//}
