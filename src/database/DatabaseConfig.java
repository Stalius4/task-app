package database;

public class DatabaseConfig {
    public static final String DB_NAME = "taskdb";
    public static final String DB_URL = "jdbc:derby:" + DB_NAME + ";create=true";
    public static final String SHUTDOWN_URL = "jdbc:derby:;shutdown=true";
}

