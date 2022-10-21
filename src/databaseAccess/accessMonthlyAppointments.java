package databaseAccess;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import reports.MonthlyAppointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class accessMonthlyAppointments {

    public static ObservableList<MonthlyAppointment> getMonthlyAppointments() {

        ObservableList<MonthlyAppointment> monthlyAppointmentList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT monthname(Start) AS Month, Type, COUNT(*) AS Total_Appointments FROM appointments GROUP BY Month, Type";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {                                               // sorting through rs to assign fields to values within the application from the database
                String date = rs.getString("Month");
                String type = rs.getString("Type");
                int totalAppointments = rs.getInt("Total_Appointments");
                MonthlyAppointment r = new MonthlyAppointment(date, type, totalAppointments);
                monthlyAppointmentList.add(r);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return monthlyAppointmentList;
    }
}
