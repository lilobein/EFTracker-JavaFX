import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class IndicatorDAO {

    public static Indicator save(Indicator indicator) throws  SQLException{
        String query = "INSERT INTO indicators (formula, value, currency_id, period_id, importance_constant) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1, indicator.getFormula());
            statement.setDouble(2, indicator.getValue());
            statement.setInt(3, indicator.getCurrency_id());
            statement.setInt(4, indicator.getPeriod_id());
            statement.setInt(5, indicator.getImportance_constant());
            statement.executeUpdate();

            try (ResultSet rs = statement.getGeneratedKeys()){
                if (rs.next()) {
                    int id = rs.getInt(1);
                    return new Indicator(
                            id,
                            indicator.getFormula(),
                            indicator.getValue(),
                            indicator.getCurrency_id(),
                            indicator.getPeriod_id(),
                            indicator.getImportance_constant()
                    );
                }
            }
        }
        throw new SQLException("Не удалось получить id");
    }


}
