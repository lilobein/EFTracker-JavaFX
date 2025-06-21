package Viewers;//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import java.io.IOException;
//
//public class SceneLogin {
//    private Parent root;  // Корневой элемент FXML
//
//    public SceneLogin() throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("loginUI.fxml"));
//        this.root = loader.load();
//    }
//
//    public Parent getRoot() {
//        return root;
//    }
//}

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Класс сцены авторизации. Загружает FXML и настраивает окно.
 */
public class SceneLogin extends Application {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final String TITLE = "Авторизация";

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("C:\\Users\\diana\\Desktop\\ProgectFX\\FinanceCS\\src\\resources\\login.fxml"));
        stage.setTitle(TITLE);
        stage.setScene(new Scene(root, WIDTH, HEIGHT));
        stage.setResizable(false);
        stage.show();
    }
}