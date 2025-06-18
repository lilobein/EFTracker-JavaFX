import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        try {


            Indicator indicator2 = new Indicator("a+b+c+d", 387900, 3, 4, 10);
            System.out.println("индикатор2 готов");

            IndicatorDAO.save(indicator2);

            indicator2.setValue(458487);
            IndicatorDAO.update(indicator2);

            IndicatorDAO.delete(indicator2);



        } catch (SQLException e) {
            System.err.println("💔 Ошибка при работе с БД: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Закрываем соединение (если нужно)
            if (DatabaseConnection.getConnection() != null) {
                DatabaseConnection.getConnection().close();
                System.out.println("\n🔌 Соединение с БД закрыто.");
            }
        }
    }
}