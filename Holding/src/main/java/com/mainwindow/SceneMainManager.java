package com.mainwindow;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.time.LocalDate;
import java.util.Optional;


public class SceneMainManager extends Application {
    private TableView<Metric> metricsTable;
    private MetricsTable model;
    private ControllerMainManager controller;

    public SceneMainManager(MetricsTable model){
        this.model = model;
    }

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/login/ManagerMainViewer.fxml"));
            Parent root = loader.load();
            metricsTable = (TableView<Metric>) root.lookup("#metricsTable");
            metricsTable.setItems(model.getTableData());
            controller = loader.getController();
            controller.setMainManager(this);
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

    public Optional<Metric> showAddMetricDialog() {
        // Создаем диалог
        Dialog<Metric> dialog = new Dialog<>();
        dialog.setTitle("Добавление нового показателя");
        dialog.setHeaderText("Введите данные показателя");

        // Создаем форму
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        // Поля ввода
        TextField nameField = new TextField();
        TextField valueField = new TextField();
        TextField currencyField = new TextField();
        TextField importanceField = new TextField();
        TextField enterpriseField = new TextField();
        DatePicker startDatePicker = new DatePicker(LocalDate.now());
        DatePicker endDatePicker = new DatePicker(LocalDate.now().plusMonths(1));

        // Добавляем поля на форму
        grid.add(new Label("Название показателя:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Значение:"), 0, 1);
        grid.add(valueField, 1, 1);
        grid.add(new Label("ID валюты:"), 0, 2);
        grid.add(currencyField, 1, 2);
        grid.add(new Label("Коэффициент важности (0-127):"), 0, 3);
        grid.add(importanceField, 1, 3);
        grid.add(new Label("ID предприятия:"), 0, 4);
        grid.add(enterpriseField, 1, 4);
        grid.add(new Label("Начало периода:"), 0, 5);
        grid.add(startDatePicker, 1, 5);
        grid.add(new Label("Конец периода:"), 0, 6);
        grid.add(endDatePicker, 1, 6);

        // Кнопки
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        dialog.getDialogPane().setContent(grid);

        // Валидация ввода
        Node okButton = dialog.getDialogPane().lookupButton(ButtonType.OK);
        okButton.setDisable(true);

        // Добавляем валидацию
        ChangeListener<String> validationListener = (observable, oldValue, newValue) -> {
            boolean isValid = !nameField.getText().trim().isEmpty()
                    && !valueField.getText().trim().isEmpty()
                    && !currencyField.getText().trim().isEmpty()
                    && !importanceField.getText().trim().isEmpty()
                    && !enterpriseField.getText().trim().isEmpty()
                    && startDatePicker.getValue() != null
                    && endDatePicker.getValue() != null
                    && startDatePicker.getValue().isBefore(endDatePicker.getValue());
            okButton.setDisable(!isValid);
        };

        // Вешаем слушатели
        nameField.textProperty().addListener(validationListener);
        valueField.textProperty().addListener(validationListener);
        currencyField.textProperty().addListener(validationListener);
        importanceField.textProperty().addListener(validationListener);
        enterpriseField.textProperty().addListener(validationListener);
        startDatePicker.valueProperty().addListener((obs, oldVal, newVal) -> validationListener.changed(null, null, null));
        endDatePicker.valueProperty().addListener((obs, oldVal, newVal) -> validationListener.changed(null, null, null));

        // Преобразуем результат
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                try {
                    return new Metric(
                            nameField.getText(),
                            Double.parseDouble(valueField.getText()),
                            Integer.parseInt(currencyField.getText()),
                            Byte.parseByte(importanceField.getText()),
                            startDatePicker.getValue(),
                            endDatePicker.getValue(),
                            Integer.parseInt(enterpriseField.getText())
                    );
                } catch (NumberFormatException e) {
                    System.out.println("говно затея");
                    return null;
                }
            }
            return null;
        });

        return dialog.showAndWait();
    }

    public TableView<Metric> getMetricsTable(){return metricsTable;}
    public MetricsTable getModel(){return model;}

    public void refreshTable() {
        model.refreshData();
    }

}


