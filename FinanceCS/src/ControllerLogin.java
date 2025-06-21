import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import java.sql.SQLException;

public class ControllerLogin {
    @FXML private TextField loginField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;

    // Инициализация (вызывается автоматически после загрузки FXML)
    @FXML
    private void initialize() {
        setupButtonStateHandler();
        setupEnterKeyHandler();
    }

    // Настройка активации кнопки при вводе данных
    private void setupButtonStateHandler() {
        loginButton.setDisable(true); // Кнопка изначально неактивна

        // Слушатели изменений в полях ввода
        loginField.textProperty().addListener((obs, oldVal, newVal) ->
                updateLoginButtonState()
        );
        passwordField.textProperty().addListener((obs, oldVal, newVal) ->
                updateLoginButtonState()
        );
    }

    // Обработка нажатия Enter (аналог клика по кнопке)
    private void setupEnterKeyHandler() {
        passwordField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER && !loginButton.isDisabled()) {
                handleLogin();
            }
        });
    }

    // Обновление состояния кнопки
    private void updateLoginButtonState() {
        boolean fieldsEmpty = loginField.getText().isEmpty() ||
                passwordField.getText().isEmpty();
        loginButton.setDisable(fieldsEmpty);
    }


    @FXML
    private void handleLogin() {
        try {
            Login login = new Login(loginField.getText().trim(), passwordField.getText().trim());
            if (login.validate()) {
                showSuccessAndOpenMainWindow();
            } else {
                showError("Неверный логин или пароль!");
                passwordField.clear();
            }
        } catch (SQLException e) {
            showError("Ошибка базы данных: " + e.getMessage());
        }
    }

    // Успешная авторизация
    private void showSuccessAndOpenMainWindow() {
        showSuccess("Авторизация успешна!");
        // Заглушка - здесь будет переход в главное окно
        // Пример: MainApp.showMainWindow();
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