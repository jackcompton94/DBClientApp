package reports;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

public class MonthlyAppointment {

    private String date;
    private String type;
    private int totalAppointments;

    public MonthlyAppointment(String date, String type, int totalAppointments) {
        this.date = date;
        this.type = type;
        this.totalAppointments = totalAppointments;
    }

    public String getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public int getTotalAppointments() {
        return totalAppointments;
    }
}
