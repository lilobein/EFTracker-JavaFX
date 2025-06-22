package com.mainwindow;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;


public class ControllerMainManager {
    @FXML private Button deleteButton;
    @FXML private Button addButton;
    @FXML private Button editButton;

    private MetricsTable model;
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
        editButton.setOnAction(e -> handleEdit());
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


    private void handleAdd() {
        Dialog<ButtonType>  dialog = view.doAddDialog();

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        if (dialog.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            try {
                model.save(view.getMetricName(), view.getMetricValue(),
                        view.getImportanceConstantValue(), view.getCurrencyId(),
                        view.getStartDateValue(), view.getEndDateValue());
                view.refreshTable();
                showConfirmation("Показатель создан.");
            } catch (Exception e){
                showError("Введены некорректные данные");
                System.out.println(e.getMessage());
                showConfirmation("Подсказка: вес важности метрики находится от 1 до 5, максимальный ID валюты - 3, минимальный - 1");

            }
        }
    }


    @FXML
    private void handleEdit() {
        Metric metricToEdit = view.getMetricsTable().getSelectionModel().getSelectedItem();
        if (metricToEdit == null) {
            showError("Показатель не выбран");
            return;
        }
        try {
            Dialog<ButtonType>  dialog = view.doEditDialog(metricToEdit);

            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
            if (dialog.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            try {
                model.letsEditMetric(metricToEdit, view.getMetricName(), view.getMetricValue(),
                        view.getImportanceConstantValue(), view.getCurrencyId(),
                        view.getStartDateValue(), view.getEndDateValue());
            } catch (Exception e) {
                System.out.println("Проверьте правильность данных");
            }
        }

        } catch (Exception e){
            System.out.println("фатальная " + e.getMessage());
        }

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