import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;


public class SceneMainManager extends Application {
    private TableView<Metric> metricsTable;
    private MetricsTable model;

    public SceneMainManager(MetricsTable model){
        this.model = model;
    }

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ManagerMainViewer.fxml"));
            Parent root = loader.load();
            metricsTable = (TableView<Metric>) root.lookup("#metricsTable");
            metricsTable.setItems(model.getTableData());
            stage.setScene(new Scene(root));
            stage.setTitle("Панель менеджера");
            stage.show();
        } catch (Exception e) {
            showErrorDialog("Ошибка запуска", e.getMessage());
            e.printStackTrace();
        }
    }

    private void showErrorDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public TableView<Metric> getMetricsTable(){return metricsTable;}

    public void refreshTable() {
        model.refreshData();
    }

}


