package databaseAccess;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import reports.ContactSchedule;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * @Author Jack Compton
 */

public abstract class accessContactSchedule {

    /**
     * provides an "ordered by start/end time" schedule of each contact from the appointments table
     *
     * @return
     * contactScheduleList
     */
    public static ObservableList<ContactSchedule> getAllContactSchedules() {

        ObservableList<ContactSchedule> contactScheduleList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Contact_ID, Appointment_ID, title, type, description, start, end, customer_id FROM client_schedule.appointments ORDER BY start, end";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){                                               // sorting through rs to assign fields to values within the application from the database
                int contactId = rs.getInt("Contact_ID");
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String type = rs.getString("Type");
                String description = rs.getString("Description");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int customerId = rs.getInt("Customer_ID");
                ContactSchedule c = new ContactSchedule(contactId, appointmentId, title, type, description, start, end, customerId);
                contactScheduleList.add(c);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return contactScheduleList;
    }
}
