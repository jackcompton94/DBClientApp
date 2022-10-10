package dataAccess;

import helper.JDBC;
import javafx.collections.FXCollections;
import model.Customers;
import javafx.collections.ObservableList;

import java.sql.*;

public class accessCustomers {

    public static ObservableList<Customers> getAllCustomers() {

        ObservableList<Customers> customerList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * FROM customers"; // query to pull all records from customers

            PreparedStatement ps = JDBC.connection.prepareStatement(sql); // assigns the database connection whilst using the query above

            ResultSet rs = ps.executeQuery();   // stores the result

            while(rs.next()){
                int customerId = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                Date createDate = rs.getDate("Create_Date");
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int divisionId = rs.getInt("Division_ID");
                Customers c = new Customers(customerId, customerName, address, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdatedBy, divisionId);
                customerList.add(c);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return customerList;
    }
}
