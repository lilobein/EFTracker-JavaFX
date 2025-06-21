package other;
import Viewers.SceneLogin;
import javafx.application.Application;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        Application.launch(SceneLogin.class, args);
    }

}