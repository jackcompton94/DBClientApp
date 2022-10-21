package reports;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

public class MonthlyAppointment {

    private String month;
    private String type;
    private int totalAppointments;

    public MonthlyAppointment(String month, String type, int totalAppointments) {
        this.month = month;
        this.type = type;
        this.totalAppointments = totalAppointments;
    }

    public String getMonth() {
        return month;
    }

    public String getType() {
        return type;
    }

    public int getTotalAppointments() {
        return totalAppointments;
    }
}
