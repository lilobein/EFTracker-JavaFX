import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CurrencyDAO {
    public static List<Currency> getAllCurrencies() throws SQLException {
        List<Currency> currencies = new ArrayList<>();
        String query = "SELECT id, name FROM currencies";

        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                currencies.add(new Currency(rs.getInt("id"), rs.getString("name")));
            }
        }
        return currencies;
    }


    public static Currency findById(int id) throws SQLException {
        String query = "SELECT name FROM currencies WHERE id = ?";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Currency(id, rs.getString("name"));
            }
        }
        return null;
    }
}