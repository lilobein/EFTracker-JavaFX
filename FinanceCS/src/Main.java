import javafx.application.Application;
import java.sql.SQLException;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws SQLException {
//        Metric metric = new Metric("тест", 400.0, 1, (byte) 1, LocalDate.of(2024, 1, 1),
//                LocalDate.of(2024, 12, 20),1 );
//        Metric metric2 = new Metric("тест2", 400.0, 1, (byte) 1, LocalDate.of(2024, 1, 1),
//                LocalDate.of(2024, 12, 20),1 );
//        MetricDAO.save(metric2);
//        MetricDAO.save(metric);
        Application.launch(SceneLogin.class, args);
    }



}