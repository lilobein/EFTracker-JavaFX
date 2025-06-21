import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class ControllerLogin {
    @FXML private TextField loginField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;

    @FXML
    private void initialize() {
        // Пример: кнопка активна только когда введены логин и пароль
        loginButton.setDisable(true);
        loginField.textProperty().addListener((obs, oldVal, newVal) ->
                updateLoginButtonState());
        passwordField.textProperty().addListener((obs, oldVal, newVal) ->
                updateLoginButtonState());
    }

    private void updateLoginButtonState() {
        boolean isDisabled = loginField.getText().isEmpty() ||
                passwordField.getText().isEmpty();
        loginButton.setDisable(isDisabled);
    }

//    @FXML
//    private void handleLoginButton() {
//        System.out.println("Логин: " + loginField.getText());
//        System.out.println("Пароль: " + passwordField.getText());
//    }
//
//    @FXML
//    private void handleLoginButton() {
//        try {
//            QueryResultWrapper result = UserDAO.findByUsernameAndPassword(
//                    loginField.getText(),
//                    passwordField.getText()
//            );
//
//            User user = (User) result.unwrap();
//            if (user != null) {
//                // Успешная авторизация
//            } else {
//                // Неверные данные
//            }
//        } catch (SQLException e) {
//            // Обработка ошибки БД
//        }//   }
}