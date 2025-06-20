//import javafx.application.Application;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
//
//public class Main extends Application {
//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        SceneLogin sceneLogin = new SceneLogin();
//        Scene scene = new Scene(sceneLogin.getRoot(), 600, 400);
//
//        primaryStage.setTitle("Авторизация");
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        String testUsername = "admin";
        String testPassword = "password123";

        try {
            System.out.println("Пытаемся найти пользователя: " + testUsername + " с паролем: " + testPassword);

            // Вызываем наш метод
            QueryResultWrapper result = UserDAO.findByUsernameAndPassword(testUsername, testPassword);

            // Получаем результат
            User user = (User) result.unwrap();

            if (user != null) {
                System.out.println("Успешная авторизация!");
                System.out.println("Найден пользователь:");
                System.out.println("ID: " + user.getUserId());
                System.out.println("Логин: " + user.getUsername());
                System.out.println("Уровень доступа: " + user.getAccessLevel());
            } else {
                System.out.println("Пользователь не найден или неверный пароль");
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при работе с БД:");
            e.printStackTrace();
        }
    }
}