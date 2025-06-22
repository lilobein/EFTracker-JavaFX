package com.mainwindow;

import java.sql.SQLException;
import java.util.List;

import com.login.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MetricsTable {
    private ObservableList<Metric> tableData;
    private User manager;

    public MetricsTable(User manager) {
        this.manager = manager;
        this.tableData = FXCollections.observableArrayList();
        refreshData();
    }

    public void save(Metric metric) throws SQLException {
        MetricDAO.save(metric);

    }

    public void update(Metric metric) throws SQLException {
        MetricDAO.update(metric);
    }

    public void delete(Metric metric) throws SQLException {
        MetricDAO.delete(metric);
    }

    public void refreshData() {
        try {
            List<Metric> metrics = MetricDAO.findByEnterpriseId(manager.getEnterpriseId());
            tableData.setAll(metrics);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Metric> getTableData() {
        return tableData;
    }

    public User getManager() {
        return manager;
    }
}