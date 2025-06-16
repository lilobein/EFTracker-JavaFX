import java.sql.*;

public class User {
    private int userId;
    private String username;
    private String password;
    private final int enterpriseId;
    private int accessLevel;
    public static final int ADMIN = 1;
    public static final int MANAGER = 2;
    public static final int ANALYST = 3;

    public User(int userId, String username, String password, int enterpriseId, int accessLevel) {
        validateAccessLevel(accessLevel);

        this.userId = userId;
        this.username = username;
        this.password = password;
        this.enterpriseId = enterpriseId;
        this.accessLevel = accessLevel;
    }


    private void validateAccessLevel(int level) {
        if (level < ADMIN || level > ANALYST) {
            throw new IllegalArgumentException(
                    "Недопустимый уровень доступа. Допустимые значения: 1 (админ), 2 (менеджер), 3 (аналитик)"
            );
        }
    }

    public void save() throws SQLException {
        String query = "INSERT INTO users (username, password, enterprise_id, access_level) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, this.username);
            stmt.setString(2, this.password);
            stmt.setInt(3, this.enterpriseId);
            stmt.setInt(4, this.accessLevel);
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    this.userId = rs.getInt(1);
                }
            }
        }
    }

    public void update() throws SQLException {
        String query = "UPDATE users SET username = ?, password = ?, access_level = ? WHERE user_id = ?";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(query)) {
            stmt.setString(1, this.username);
            stmt.setString(2, this.password);
            stmt.setInt(3, this.accessLevel);
            stmt.setInt(4, this.userId);
            stmt.executeUpdate();
        }
    }


    public static QueryResultWrapper findById(int userId) throws SQLException {
        String query = "SELECT * FROM users WHERE user_id = ?";
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
        String query = "DELETE FROM users WHERE user_id = ?";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        }
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Integer getEnterpriseId() {
        return enterpriseId;
    }

    public int getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(int accessLevel) {
        validateAccessLevel(accessLevel);
        this.accessLevel = accessLevel;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password){
        this.password = password;
    }
    public boolean isAdmin() {
        return accessLevel == ADMIN;
    }

    public boolean isManager() {
        return accessLevel == MANAGER;
    }

    public boolean isAnalyst() {
        return accessLevel == ANALYST;
    }

    @Override
    public String toString() {
        String roleName = switch (accessLevel) {
            case ADMIN -> "ADMIN";
            case MANAGER -> "MANAGER";
            case ANALYST -> "ANALYST";
            default -> "UNKNOWN";
        };

        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", enterpriseId=" + enterpriseId +
                ", accessLevel=" + roleName +
                '}';
    }
}