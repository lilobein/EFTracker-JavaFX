import java.sql.*;
import java.sql.SQLException;

public class UserDAO {
    public static User save(User user) throws SQLException {
        String query = "INSERT INTO users (username, password, enterprise_id, access_level) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setInt(3, user.getEnterpriseId());
            stmt.setInt(4, user.getAccessLevel());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    return new User(
                            generatedId,
                            user.getUsername(),
                            user.getPassword(),
                            user.getEnterpriseId(),
                            user.getAccessLevel()
                    );
                }
            }
        }
        throw new SQLException("Не удалось получить ID после сохранения");
    }

    public static void update(User user) throws SQLException {
        String query = "UPDATE users SET username=?, password=?, access_level=? WHERE user_id=?";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(query)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setInt(3, user.getAccessLevel());
            stmt.setInt(4, user.getUserId());
            stmt.executeUpdate();
        }
    }


    public static QueryResultWrapper findById(int userId) throws SQLException {
        String query = "SELECT * FROM users WHERE user_id=?";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            QueryResultWrapper wrapper = QueryResultWrapper.getInstance();
            if (rs.next()) {
                User user = new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getInt("enterprise_id"),
                        rs.getInt("access_level")
                );
                wrapper.wrap(user);
            } else {
                wrapper.wrap(null);
            }
            return wrapper;
        }
    }


    public static void delete(int userId) throws SQLException {
        String query = "DELETE FROM users WHERE user_id=?";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        }
    }

}