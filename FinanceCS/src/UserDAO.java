import java.sql.*;
import java.sql.SQLException;

public class UserDAO {

    public static void createUser(User user) throws SQLException {
        String sql = "INSERT INTO users (username, password, enterprise_id, access_level) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setObject(3, user.getEnterpriseId(), Types.INTEGER);
            stmt.setInt(4, user.getAccessLevel());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    user.setId(rs.getInt(1));
                }
            }
        } throw new SQLException("Что-то не получилось - лучше разобраться в причине");
    }

    public static void update(User user) throws SQLException {
        String query = "UPDATE users SET username=?, password=?, access_level=? WHERE id=?";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(query)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setInt(3, user.getAccessLevel());
            stmt.setInt(4, user.getUserId());
            stmt.executeUpdate();
        }
    }

    public static QueryResultWrapper findById(int userId) throws SQLException {
        String query = "SELECT * FROM users WHERE id=?";
        QueryResultWrapper wrapper = QueryResultWrapper.getInstance();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User(
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getObject("enterprise_id", Integer.class),
                            rs.getInt("access_level")
                    );
                    user.setId(rs.getInt("id"));
                    wrapper.wrap(user);
                } else {
                    wrapper.wrap(null);
                }
            }
        } catch (SQLException e) {
            wrapper.wrap(null);
            throw e;
        }
        return wrapper;
    }

    public static void delete(User user) throws SQLException {
        String query = "DELETE FROM users WHERE id=?";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(query)) {
            stmt.setInt(1, user.getUserId());
            stmt.executeUpdate();
        }
    }

}