package reports;

import java.time.LocalDateTime;

public class ContactSchedule {

    private int contactId;
    private int appointmentId;
    private String title;
    private String type;
    private String description;
    private LocalDateTime start;
    private LocalDateTime end;
    private int customerId;

    public ContactSchedule(int contactId, int appointmentId, String title, String type, String description, LocalDateTime start, LocalDateTime end, int customerId) {
        this.contactId = contactId;
        this.appointmentId = appointmentId;
        this.title = title;
        this.type = type;
        this.description = description;
        this.start = start;
        this.end = end;
        this.customerId = customerId;
    }

    public int getContactId() {
        return contactId;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public int getCustomerId() {
        return customerId;
    }
}
