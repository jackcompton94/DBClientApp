package databaseAccess;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import java.sql.*;
import java.time.LocalDateTime;

public abstract class accessAppointments {

    public static ObservableList<Appointment> getAllAppointments() {

        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * FROM appointments";                      // query to pull all records from appointments
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);   // assigns the database connection whilst using the query above to ps
            ResultSet rs = ps.executeQuery();                               // stores the result in rs

            while(rs.next()){                                               // sorting through rs to assign fields to values within the application from the database
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                Timestamp createDate = rs.getTimestamp("Create_Date");
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");
                Appointment a = new Appointment(appointmentId, title, description, location, type, start, end, createDate, createdBy, lastUpdate, lastUpdatedBy, customerId, userId, contactId);
                appointmentList.add(a);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentList;
    }

    public static int insert(String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy, int customerId, int userId, int contactId) throws SQLException {
        String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, Timestamp.valueOf(start));
        ps.setTimestamp(6, Timestamp.valueOf(end));
        ps.setTimestamp(7,Timestamp.valueOf(createDate));
        ps.setString(8,createdBy);
        ps.setTimestamp(9,Timestamp.valueOf(lastUpdate));
        ps.setString(10,lastUpdatedBy);
        ps.setInt(11,customerId);
        ps.setInt(12,userId);
        ps.setInt(13, contactId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    // TODO: Update
    // TODO: Delete
}
