package databaseAccess;

import helper.JDBC;
import javafx.collections.FXCollections;
import model.Customer;
import javafx.collections.ObservableList;
import java.sql.*;

/**
 * @Author Jack Compton
 */

public abstract class accessCustomers {

    /**
     * allows Return functionality in the application from the database
     *
     * @return
     * all available rows in the customers table
     */
    public static ObservableList<Customer> getAllCustomers() {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * FROM customers";                         // query to pull all records from customers
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);   // assigns the database connection whilst using the query above to ps
            ResultSet rs = ps.executeQuery();                               // stores the result in rs

            while(rs.next()){                                               // sorting through rs to assign fields to values within the application from the database
                int customerId = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                Timestamp createDate = rs.getTimestamp("Create_Date");
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int divisionId = rs.getInt("Division_ID");
                Customer c = new Customer(customerId, customerName, address, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdatedBy, divisionId);
                customerList.add(c);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customerList;
    }

    /**
     *
     * insert method used in AddCustomer(), allows Create functionality in the application to the database
     *
     * @return
     * rowsAffected
     * @throws SQLException
     * An exception that provides information on a database access error or other errors.
     */
    public static int insert(String customerName, String address, String postalCode, String phone, Timestamp createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy, int divisionId) throws SQLException {
        String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES (?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setTimestamp(5,createDate);
        ps.setString(6,createdBy);
        ps.setTimestamp(7,lastUpdate);
        ps.setString(8,lastUpdatedBy);
        ps.setInt(9,divisionId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * update method used to provide Update functionality in the application from the database
     *
     * @return
     * rowsAffected
     *
     * @throws SQLException
     * An exception that provides information on a database access error or other errors.
     */
    public static int update(String customerName, String address, String postalCode, String phone, Timestamp lastUpdate, String lastUpdatedBy, int divisionId, int customerId) throws SQLException {
        String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setTimestamp(5,lastUpdate);
        ps.setString(6,lastUpdatedBy);
        ps.setInt(7, divisionId);
        ps.setInt(8, customerId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * delete method provides application Delete functionality in application from the database
     *
     * @return
     * rowsAffected
     *
     * @throws SQLException
     * An exception that provides information on a database access error or other errors.
     */
    public static int delete(int customerId) throws SQLException {
        String sql = "DELETE FROM customers WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
}
