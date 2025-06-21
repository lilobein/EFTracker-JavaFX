import java.sql.SQLException;

public class Login {
    private String username;
    private String password;

    public Login(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean validate() throws SQLException {
        QueryResultWrapper result = UserDAO.findByUsername(username);
        User user = (User) result.unwrap();

        if (user == null || !user.getPassword().equals(password)) {
            return false;
        }
        return true;
    }


    public String getUsername() { return username; }
    public String getPassword() { return password; }
}