import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Enterprise {
    private int id;
    private String name;
    private String contactPerson;
    private String details;
    private String phone;

    public Enterprise() {}

    public Enterprise(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Enterprise(int id, String name, String contactPerson, String details, String phone) {
        this.id = id;
        this.name = name;
        this.contactPerson = contactPerson;
        this.details = details;
        this.phone = phone;
    }


    public Enterprise getEnterprise(){return this;}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Название предприятия не может быть пустым");
        }
        this.name = name;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean hasContactPerson() {
        return contactPerson != null && !contactPerson.trim().isEmpty();
    }

    public static QueryResultWrapper findById(int id) throws SQLException {
        String query = "SELECT * FROM users WHERE user_id = ?";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            QueryResultWrapper wrapper = QueryResultWrapper.getInstance();

            if (rs.next()) {
                Enterprise enterprise = new Enterprise(
                        rs.getInt("enterprise_id"),
                        rs.getString("name"),
                        rs.getString("contactPerson"),
                        rs.getString("details"),
                        rs.getString("phone")
                );
                wrapper.wrap(enterprise);
            } else {
                wrapper.wrap(null);
            }
            return wrapper;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Enterprise{id=").append(id)
                .append(", name='").append(name).append('\'');

        if (contactPerson != null) {
            sb.append(", contactPerson='").append(contactPerson).append('\'');
        }
        if (phone != null) {
            sb.append(", phone='").append(phone).append('\'');
        }

        sb.append('}');
        return sb.toString();
    }
}