import java.sql.*;

    public class DatabaseConnection {
        private static Connection connection;
        private static final String URL = "jdbc:mysql://127.0.0.1:3306/financial_holding";
        private static final String USER = "root";
        private static final String PASSWORD = "";

        public static Connection getConnection() {
            if (connection == null) {
                try {
                    connection = DriverManager.getConnection(URL, USER, PASSWORD);
                    System.out.println("Успешное подключение к БД!");
                } catch (SQLException e) {
                    System.err.println("Ошибка подключения: " + e.getMessage());
                }
            }
            return connection;
        }
    }

