import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;

public class SceneLogin {
    private Parent root;

    public SceneLogin() throws IOException {
        root = FXMLLoader.load(getClass().getResource("loginUI.fxml"));
    }

    public Parent getRoot() {
        return root;
    }
}