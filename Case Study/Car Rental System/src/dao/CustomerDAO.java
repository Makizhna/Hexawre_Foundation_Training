package dao;

import entity.Customer;
import util.DBConnection;
import java.sql.*;

public class CustomerDAO {

    public Customer getCustomerByID(int customerID) {
        String query = "SELECT * FROM customers WHERE customer_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, customerID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {  // If a record is found
                return new Customer(
                    rs.getInt("customer_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email"),
                    rs.getString("phone_number")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;  // If no customer is found
    }
}
