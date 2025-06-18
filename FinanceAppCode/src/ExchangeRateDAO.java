import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExchangeRateDAO {
    public static List<ExchangeRate> getAllRates() throws SQLException {
        List<ExchangeRate> rates = new ArrayList<>();
        String query = "SELECT currency_from_id, currency_to_id, rate FROM exchange_rates";

        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Currency from = CurrencyDAO.findById(rs.getInt("currency_from_id"));
                Currency to = CurrencyDAO.findById(rs.getInt("currency_to_id"));
                rates.add(new ExchangeRate(from, to, rs.getDouble("rate")));
            }
        }
        return rates;
    }
}