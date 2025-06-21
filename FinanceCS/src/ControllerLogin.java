import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.sql.SQLException;

public class ControllerLogin {
    @FXML private TextField loginField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    private Login login;


    @FXML
    private void initialize() {
        setupButtonStateHandler();
        setupEnterKeyHandler();
    }

    private void setupButtonStateHandler() {
        loginButton.setDisable(true); // Кнопка изначально неактивна
        loginField.textProperty().addListener((obs, oldVal, newVal) ->
                updateLoginButtonState()
        );
        passwordField.textProperty().addListener((obs, oldVal, newVal) ->
                updateLoginButtonState()
        );
    }


    private void setupEnterKeyHandler() {
        passwordField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER && !loginButton.isDisabled()) {
                handleLogin();
            }
        });
    }

    private void updateLoginButtonState() {
        boolean fieldsEmpty = loginField.getText().isEmpty() ||
                passwordField.getText().isEmpty();
        loginButton.setDisable(fieldsEmpty);
    }


    @FXML
    private void handleLogin() {
        try {
            Login login = new Login(loginField.getText(), passwordField.getText());
            if (login.validate()) {
                Stage currentStage = (Stage) loginButton.getScene().getWindow();
                currentStage.close();
                openManagerWindow();
            } else {
                showError("Неверный логин или пароль!");
            }
        } catch (SQLException e) {
            showError("Ошибка базы данных: " + e.getMessage());
        }
    }

    private void openManagerWindow() {
        try {
            Stage managerStage = new Stage();
            SceneMainManager managerScene = new SceneMainManager();
            managerScene.start(managerStage);
        } catch (Exception e) {
            showError("Ошибка открытия панели менеджера: " + e.getMessage());
        }
    }

    private void showError(String message) {
        new Alert(Alert.AlertType.ERROR, message).showAndWait();
    }

    private void showSuccess(String message) {
        System.out.println(message);
        // Можно заменить на Alert:
        // new Alert(Alert.AlertType.INFORMATION, message).showAndWait();
    }
}