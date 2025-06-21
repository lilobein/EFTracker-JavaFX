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

//public class Main {
//    public static void main(String[] args) {
//        String testUsername = "admin";
//        String testPassword = "password123";
//
//        try {
//            System.out.println("Пытаемся найти пользователя: " + testUsername + " с паролем: " + testPassword);
//
//            // Вызываем наш метод
//            QueryResultWrapper result = UserDAO.findByUsernameAndPassword(testUsername, testPassword);
//
//            // Получаем результат
//            User user = (User) result.unwrap();
//
//            if (user != null) {
//                System.out.println("Успешная авторизация!");
//                System.out.println("Найден пользователь:");
//                System.out.println("ID: " + user.getId());
//                System.out.println("Логин: " + user.getUsername());
//                System.out.println("Уровень доступа: " + user.getAccessLevel());
//            } else {
//                System.out.println("Пользователь не найден или неверный пароль");
//            }
//        } catch (SQLException e) {
//            System.err.println("Ошибка при работе с БД:");
//            e.printStackTrace();
//        }
//    }
//}

import java.sql.SQLException;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws SQLException {
        Metric testMetric = new Metric(
                "Рентабельность продаж",  // metric_name
                15.75,                     // value - процент рентабельности
                1,                         // currency_id (1 - RUB)
                (byte) 3,                  // importance_constant (по шкале 1-5)
                LocalDate.of(2023, 1, 1),  // period_start
                LocalDate.of(2023, 12, 31),// period_end
                1                          // enterprise_id (ID предприятия)
        );

        try {
            MetricDAO.save(testMetric);
        } catch (SQLException e){
            System.out.println("Говно " + e.getMessage());
        }

    }
}