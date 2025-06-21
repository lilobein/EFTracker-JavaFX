package Viewers;

import Controllers.ControllerMainManager;
import Dao.MetricDAO;
import Models.Metric;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import java.sql.SQLException;

public class SceneMainManager extends Application {
    private TableView<Metric> metricsTable;
    private ControllerMainManager controller;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("C:\\Users\\diana\\Desktop\\ProgectFX\\FinanceCS\\src\\resources\\ManagerMainViewer.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        controller.setMainManager(this);
        metricsTable = (TableView<Metric>) root.lookup("#metricsTable");
        refreshTable();
        stage.setScene(new Scene(root));
        stage.setTitle("Панель менеджера");
        stage.show();
    }

    public void refreshTable() {
        try {
            metricsTable.getItems().setAll(MetricDAO.findAll());
        } catch (SQLException e) {
//            controller.showAlert("Ошибка", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}