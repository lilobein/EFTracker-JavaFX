package Controllers;

import Dao.MetricDAO;
import Models.Metric;
import Viewers.SceneMainManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import java.sql.SQLException;
import java.time.LocalDate;

public class ControllerMainManager {
    @FXML private TableView<Metric> metricsTable;
    @FXML private Button addButton;
    @FXML private Button deleteButton;
    @FXML private Button editButton;

    private int enterpriseId;
    private SceneMainManager mainManager; // Ссылка на главную сцену

    @FXML
    private void initialize() {
        setupButtonActions();
    }


    public void setMainManager(SceneMainManager mainManager) {
        this.mainManager = mainManager;
    }

    private void setupButtonActions() {
        addButton.setOnAction(event -> handleAddMetric());
        deleteButton.setOnAction(event -> handleDeleteMetric());
        editButton.setOnAction(event -> handleEditMetric());
    }

    private void handleAddMetric() {
        try {
            // Создаем новую метрику с default значениями
            Metric newMetric = new Metric(
                    "Новый показатель",
                    0.0,
                    1, // ID валюты по умолчанию
                    (byte)1,
                    LocalDate.now(),
                    LocalDate.now().plusMonths(1),
                    enterpriseId
            );

            if (showMetricDialog(newMetric)) {
                MetricDAO.save(newMetric);
                mainManager.refreshTable(); // Используем метод сцены
                showAlert("Успех", "Показатель добавлен", AlertType.INFORMATION);
            }
        } catch (SQLException e) {
            showAlert("Ошибка", e.getMessage(), AlertType.ERROR);
        }
    }

    private void handleDeleteMetric() {
        Metric selected = metricsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Ошибка", "Выберите показатель", AlertType.WARNING);
            return;
        }

        try {
            MetricDAO.delete(selected);
            mainManager.refreshTable();
            showAlert("Успех", "Показатель удален", AlertType.INFORMATION);
        } catch (SQLException e) {
            showAlert("Ошибка", e.getMessage(), AlertType.ERROR);
        }
    }

    private void handleEditMetric() {
        Metric selected = metricsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Ошибка", "Выберите показатель", AlertType.WARNING);
            return;
        }

        try {
            // Создаем копию через конструктор
            Metric editedMetric = new Metric(
                    selected.getMetric_name(),
                    selected.getValue(),
                    selected.getCurrency_id(),
                    selected.getImportance_constant(),
                    selected.getPeriod_start(),
                    selected.getPeriod_end(),
                    selected.getEnterprise_id()
            );
            editedMetric.setId(selected.getId());

            if (showMetricDialog(editedMetric)) {
                MetricDAO.update(editedMetric);
                mainManager.refreshTable();
                showAlert("Успех", "Изменения сохранены", AlertType.INFORMATION);
            }
        } catch (SQLException e) {
            showAlert("Ошибка", e.getMessage(), AlertType.ERROR);
        }
    }

    private boolean showMetricDialog(Metric metric) {
        // Заглушка для диалога редактирования
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Редактирование");
        alert.setHeaderText("Редактирование показателя: " + metric.getMetric_name());
        alert.setContentText("Здесь будет форма редактирования");

        return alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK;
    }

    void showAlert(String title, String message, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void setEnterpriseId(int enterpriseId) {
        this.enterpriseId = enterpriseId;
    }


}