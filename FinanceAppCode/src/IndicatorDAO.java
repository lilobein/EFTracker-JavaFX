import java.sql.*;

public class IndicatorDAO {
    public static void save(Indicator indicator) throws  SQLException{
        String query = "INSERT INTO indicators (formula, value, currency_id, period_id, importance_constant) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1, indicator.getFormula());
            statement.setDouble(2, indicator.getValue());
            statement.setInt(3, indicator.getCurrency_id());
            statement.setInt(4, indicator.getPeriod_id());
            statement.setDouble(5, indicator.getImportance_constant());
            statement.executeUpdate();
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    indicator.setIndicator_id(rs.getInt(1));
                }
            }

        }

    }

    public static QueryResultWrapper findById(int indicatorsId) throws SQLException{
        String query = "SELECT * FROM indicators WHERE indicator_id=?";
        QueryResultWrapper wrapper = QueryResultWrapper.getInstance();
        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, indicatorsId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Indicator indicator = new Indicator(
                            rs.getString("formula"),
                            rs.getDouble("value"),
                            rs.getInt("currency_id"),
                            rs.getInt("period_id"),
                            rs.getDouble("importance_constant")
                    );
                    indicator.setIndicator_id(rs.getInt("indicator_id"));
                    wrapper.wrap(indicator);
                } else {
                    wrapper.wrap(null);
                }

            }
        } catch (SQLException e){
            wrapper.wrap(null);
            throw e;
        }
        return wrapper;
    }

    public static void update(Indicator indicator) throws SQLException {
        String query = "UPDATE indicators SET formula=?, value=?, currency_id=?, period_id=?, importance_constant=? WHERE indicator_id=?";
        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(query)) {
            statement.setString(1, indicator.getFormula());
            statement.setDouble(2, indicator.getValue());
            statement.setInt(3, indicator.getCurrency_id());
            statement.setInt(4, indicator.getPeriod_id());
            statement.setDouble(5, indicator.getImportance_constant());
            statement.setInt(6, indicator.getIndicator_id());
            statement.executeUpdate();
        }
    }

    public static void delete(Indicator indicator) throws  SQLException{
        String query = "DELETE FROM indicators WHERE indicator_id=?";
        try(PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(query)){
            statement.setInt(1, indicator.getIndicator_id());
            statement.executeUpdate();
        }
    }


}
