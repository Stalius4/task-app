package database;

import java.sql.*;

public class DatabaseInitializer {

    public static void initializeDatabase() {
        String createTaskTable = """
            CREATE TABLE tasks (
                id VARCHAR(36) PRIMARY KEY,
                title VARCHAR(255) NOT NULL,
                description CLOB,
                created_date DATE NOT NULL,
                is_completed BOOLEAN DEFAULT FALSE
            )
        """;

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement()) {

            if (!tableExists(conn, "TASKS")) {
                stmt.executeUpdate(createTaskTable);
                System.out.println("✓ Tasks table created");
            } else {
                System.out.println("✓ Tasks table already exists");
            }

        } catch (SQLException e) {
            System.err.println("Database initialization failed: " + e.getMessage());
            throw new RuntimeException("Cannot initialize database", e);
        }
    }

    private static boolean tableExists(Connection conn, String tableName) throws SQLException {
        DatabaseMetaData meta = conn.getMetaData();
        try (ResultSet rs = meta.getTables(null, null, tableName, new String[]{"TABLE"})) {
            return rs.next();
        }
    }
}
