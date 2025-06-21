import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.sql.SQLException;
import java.time.LocalDate;

//public class SceneMainManager extends Application {
//    private static final int WIDTH = 800;
//    private static final int HEIGHT = 600;
//    private static final String TITLE = "Панель менеджера";
//
//    @Override
//    public void start(Stage stage) throws SQLException {
//        BorderPane root = new BorderPane();
//        stage.setScene(new Scene(root, WIDTH, HEIGHT));
//        stage.setTitle(TITLE);
//        stage.setResizable(false);
//        stage.show();
//    }


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.SQLException;
import java.util.List;

public class SceneMainManager extends Application {
    private static final String TITLE = "Панель менеджера";

//    @Override
//    public void start(Stage stage) throws Exception {
//        // Загружаем FXML (убедитесь, что путь правильный)
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("ManagerMainViewer.fxml"));
//        Parent root = loader.load();
//
//        // Получаем таблицу из FXML
//        TableView<Metric> metricsTable = (TableView<Metric>) root.lookup("#metricsTable");
//
//        // Загружаем данные из БД
//        ObservableList<Metric> metricsData = FXCollections.observableArrayList();
//        try {
//            List<Metric> metrics = MetricDAO.findAll();
//            System.out.println("Загружено записей: " + metrics.size());
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        metricsTable.setItems(metricsData);
//
//        // Настраиваем сцену
//        stage.setScene(new Scene(root));
//        stage.setTitle(TITLE);
//        stage.setResizable(false);
//        stage.show();
//    }

    @Override
    public void start(Stage stage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("ManagerMainViewer.fxml"));

            // Проверка загрузки TableView
            TableView<Metric> table = (TableView<Metric>) root.lookup("#metricsTable");
            if (table == null) {
                throw new RuntimeException("TableView не найден!");
            }

            // Загрузка данных
            loadTableData(table);

            stage.setScene(new Scene(root));
            stage.setTitle("Панель менеджера");
            stage.show();

        } catch (Exception e) {
            showErrorDialog("Ошибка запуска", e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadTableData(TableView<Metric> table) {
        try {
            table.setItems(FXCollections.observableArrayList(MetricDAO.findAll()));
        } catch (SQLException e) {
            showErrorDialog("Ошибка БД", e.getMessage());
        }
    }

    private void showErrorDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }


}
