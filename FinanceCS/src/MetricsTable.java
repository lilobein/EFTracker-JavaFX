
import java.sql.SQLException;
import java.util.List;
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
        refreshData();
    }

    public void update(Metric metric) throws SQLException {
        MetricDAO.update(metric);
        refreshData();
    }

    public void delete(Metric metric) throws SQLException {
        MetricDAO.delete(metric);
        refreshData();
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