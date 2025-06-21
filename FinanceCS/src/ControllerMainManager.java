import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

public class ControllerMainManager {  // Проверьте точное написание имени класса!
    @FXML
    private TableView<?> metricsTable;  // Замените <?> на ваш класс данных (например, Metric)

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    @FXML
    private void initialize() {
        System.out.println("Контроллер инициализирован успешно!");
        // Проверка инициализации
        if (metricsTable == null) System.err.println("TableView не инициализирован!");
        if (addButton == null) System.err.println("Кнопка добавления не инициализирована!");
    }
}