import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import java.sql.SQLException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;


public class SceneMainManager extends Application {
    private User user;
    private static final String TITLE = "Панель менеджера";
    @Override
    public void start(Stage stage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("ManagerMainViewer.fxml"));
            TableView<Metric> table = (TableView<Metric>) root.lookup("#metricsTable");
            if (table == null) {
                throw new RuntimeException("TableView не найден!");
            }
            loadTableData(table, Login.getUser());
            stage.setScene(new Scene(root));
            stage.setTitle("Панель менеджера");
            stage.show();
        } catch (Exception e) {
            showErrorDialog("Ошибка запуска", e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadTableData(TableView<Metric> table, User user) {
        this.user = user;
        try {
            table.setItems(FXCollections.observableArrayList(MetricDAO.findByEnterpriseId(user.getEnterpriseId())));
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
