package com.mainwindow;

import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;


public class ControllerMainManager {
    @FXML private Button deleteButton;
    @FXML private Button addButton;
    @FXML private Button editButton;

    private static MetricsTable model;
    private SceneMainManager view;


    @FXML
    private void initialize() {
        setupButtonActions();

    }

    public void setModel(MetricsTable model){
        this.model = model;
    }

    public void setMainManager(SceneMainManager mainManager) {
        this.view = mainManager;
        setModel(view.getModel());
    }

    private void setupButtonActions() {
        deleteButton.setOnAction(e -> handleDelete());
        addButton.setOnAction(e -> handleAdd());

    }

    private void handleDelete() {
        Metric selected = view.getMetricsTable().getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Выберите показатель для удаления");
            return;
        }

        if (showConfirmation("Удалить выбранный показатель?")) {
            try {
                model.delete(selected);
                view.refreshTable();
                view.getMetricsTable().getItems().remove(selected);
            } catch (SQLException e) {
                showError("Ошибка удаления: " + e.getMessage());
            }
        }
    }


    @FXML
    private void handleAdd() {
        Optional<Metric> result = view.showAddMetricDialog();
        if (result.isPresent()) {
            Metric newMetric = result.get();
            try {
                model.save(newMetric);
                view.refreshTable();
                System.out.println("норм");
            } catch (SQLException e) {
                System.out.println("насрала");
            }
        }
    }

//
//    private void handleAdd() {
//        try {
//            Metric newMetric = createNewMetric();
//            if (showConfirmation("Добавить новый показатель?")) {
//                model.save(newMetric);
//                view.refreshTable();
//            }
//        } catch (SQLException e) {
//            showError("Ошибка добавления: " + e.getMessage());
//        }
//    }

    private void handleEdit() {
        Metric selected = view.getMetricsTable().getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Выберите показатель для редактирования");
            return;
        }
        try {
            Metric editedMetric = editMetric(selected);
            if (editedMetric != null) {
                model.update(editedMetric);
                view.refreshTable();
            }
        } catch (SQLException e) {
            showError("Ошибка редактирования: " + e.getMessage());
        }
    }

    private Metric createNewMetric() {
        return new Metric("Новый показатель", 0.0, 1, (byte)1,
                LocalDate.now(), LocalDate.now().plusMonths(1),
                model.getManager().getEnterpriseId());
    }

    private Metric editMetric(Metric metric) {
        // Реализуйте диалог редактирования
        return metric; // Заглушка
    }

    private boolean showConfirmation(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(message);
        return alert.showAndWait().get() == ButtonType.OK;
    }

    private void showError(String message) {
        new Alert(Alert.AlertType.ERROR, message).showAndWait();
    }
}