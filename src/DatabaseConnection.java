import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/BloodDonationDB", "root", "Vijay@2005");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
