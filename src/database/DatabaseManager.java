package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class DatabaseManager {

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DatabaseConfig.DB_URL);
    }

    public static void shutdown() {
        try {
            DriverManager.getConnection(DatabaseConfig.SHUTDOWN_URL);
        } catch (SQLException e) {
            if (!"XJ015".equals(e.getSQLState())) {
                System.err.println("Shutdown error: " + e.getMessage());
            }
        }
    }

    // New method to insert a simple string as a task
    public static void insertSimpleTask(String title) {
        String insertSQL = "INSERT INTO tasks (id, title, created_date, is_completed) VALUES (?, ?, CURRENT_DATE, FALSE)";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            
            pstmt.setString(1, UUID.randomUUID().toString());
            pstmt.setString(2, title);
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("✓ Task inserted successfully: " + title);
            }
            
        } catch (SQLException e) {
            System.err.println("Error inserting task: " + e.getMessage());
        }
    }
}
